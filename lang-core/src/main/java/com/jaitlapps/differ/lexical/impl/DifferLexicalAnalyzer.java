package com.jaitlapps.differ.lexical.impl;

import com.google.common.collect.ImmutableSet;
import com.jaitlapps.differ.lexical.LexicalAnalyzer;
import com.jaitlapps.differ.model.ExpressionType;
import com.jaitlapps.differ.model.KeywordType;
import com.jaitlapps.differ.model.Token;
import com.jaitlapps.differ.model.TokenType;
import com.jaitlapps.differ.reader.Reader;

import java.util.Set;

public class DifferLexicalAnalyzer implements LexicalAnalyzer {
    private static final Set<String> KEYWORDS = ImmutableSet.<String>builder()
            .add("начало")
            .add("первое")
            .add("второе")
            .add("третье")
            .add("сочетаемое")
            .build();

    private final Set<String> EXPRESSIONS = ImmutableSet.<String>builder()
            .add("+")
            .add("-")
            .add("*")
            .add("/")
            .add("^")
            .add("]")
            .add("[")
            .build();

    private Reader reader;
    private Token prevToken;

    public DifferLexicalAnalyzer(Reader reader) {
        this.reader = reader;
    }

    @Override
    public Token nextToken() {
        Token token = new Token();
        token.setPrevToken(prevToken);

        String nextWord = reader.readNextWord();

        if (nextWord != null) {
            token.setSourceString(nextWord);
            token.setCapitalizedString(nextWord.toLowerCase());

            token.setStartPosition(reader.getStartPosition());
            token.setEndPosition(reader.getEndPosition());

            token = DetermineTypeToken(token);
        } else {
            token.setTokenType(TokenType.Eof);
        }

        prevToken = token;

        return token;
    }

    // TODO: REFACTOR ME PLEASE!!!!!
    private Token DetermineTypeToken(Token token) {
        Integer tokenInteger = TryDetermineInteger(token.getCapitalizedString());

        String tokenKeyword = TryDetermineKeyword(token.getCapitalizedString());
        Double tokenReal = TryDetermineReal(token.getCapitalizedString());
        String tokenVariable = TryDetermineVariable(token.getCapitalizedString());
        String tokenLabel = TryDetermineLabel(token.getCapitalizedString());
        String tokenExpression = TryDetermineExpression(token.getCapitalizedString());
        String tokenEqual = tryDetermineEqual(token.getCapitalizedString());
        String tokenEndLine = tryDetermineEndLine(token.getCapitalizedString());

        if (",".equals(token.getCapitalizedString())) {
            token.setTokenType(TokenType.Comma);
        } else if (tokenReal != null) {
            token.setTokenType(TokenType.Real);
            token.setValueReal(tokenReal);
        } else if (tokenInteger != null) {
            token.setTokenType(TokenType.Integer);
            token.setValue(tokenInteger);
        } else if (tokenKeyword != null) {
            token.setTokenType(TokenType.Keyword);

            if (tokenKeyword.equals("начало")) {
                token.setKeywordType(KeywordType.Begin);
            } else if (tokenKeyword.equals("третье")) {
                token.setKeywordType(KeywordType.Third);
            } else if (tokenKeyword.equals("второе")) {
                token.setKeywordType(KeywordType.Second);
            } else if (tokenKeyword.equals("первое")) {
                token.setKeywordType(KeywordType.First);
            } else if (tokenKeyword.equals("сочетаемое")) {
                token.setKeywordType(KeywordType.Combine);
            }
        } else if (tokenVariable != null) {
            token.setTokenType(TokenType.Variable);
        } else if (tokenLabel != null) {
            token.setTokenType(TokenType.Label);
        } else if (tokenExpression != null) {
            token.setTokenType(TokenType.Expression);

            if (tokenExpression.equals("+")) {
                token.setExpressionType(ExpressionType.Addition);
            } else if (tokenExpression.equals("-")) {
                token.setExpressionType(ExpressionType.Subtraction);
            } else if (tokenExpression.equals("*")) {
                token.setExpressionType(ExpressionType.Multiplication);
            } else if (tokenExpression.equals("/")) {
                token.setExpressionType(ExpressionType.Division);
            } else if (tokenExpression.equals("^")) {
                token.setExpressionType(ExpressionType.Involution);
            } else if (tokenExpression.equals("[")) {
                token.setExpressionType(ExpressionType.OpenBracket);
            } else if (tokenExpression.equals("]")) {
                token.setExpressionType(ExpressionType.CloseBracket);
            }
        } else if (tokenEqual != null) {
            token.setTokenType(TokenType.Equal);
        } else if (tokenEndLine != null) {
            token.setTokenType(TokenType.EndLine);
        } else {
            token.setTokenType(TokenType.Unknown);
        }

        return token;
    }

    private static Integer TryDetermineInteger(String token) {
        try {
            return Integer.valueOf(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String TryDetermineKeyword(String token) {
        String result = null;

        if (KEYWORDS.contains(token)) {
            result = token;
        }

        return result;
    }

    // TODO: replace this by regex
    private static String TryDetermineVariable(String token) {
        boolean isVariable = true;

        char[] tmpCharArr = token.toCharArray();

        if (IsLatinLetter(tmpCharArr[0])) {
            int pos = 1;

            while (pos < token.length() && isVariable) {
                if (IsLatinLetterOrDigit(tmpCharArr[pos])) {
                    pos++;
                } else {
                    isVariable = false;
                }
            }
        } else {
            isVariable = false;
        }

        if (isVariable) {
            return token;
        }

        return null;
    }

    private static boolean IsLatinLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
                || (ch >= 'а' && ch <= 'я') || (ch >= 'А' && ch <= 'Я');

    }

    private static boolean IsLatinLetterOrDigit(char ch)
    {
        return IsDigit(ch) || IsLatinLetter(ch);

    }

    private static boolean IsDigit(char ch)
    {
        return (ch >= '1' && ch <= '9');

    }

    private static Double TryDetermineReal(String token) {
        Double result = null;

        if (token.matches("\\d+\\.\\d+")) {
            result = Double.valueOf(token);
        }

        return result;
    }

    private static String TryDetermineLabel(String token) {
        boolean isLabel = true;

        int posEnd = token.length() - 1;

        char[] tmpCharArr = token.toCharArray();

        if (tmpCharArr[posEnd] == ':') {
            posEnd--;

            while (posEnd > 0 && isLabel) {
                if (Character.isDigit(tmpCharArr[posEnd])) {
                    posEnd--;
                } else {
                    isLabel = false;
                }
            }
        } else {
            isLabel = false;
        }

        if (isLabel)
            return token;

        return null;
    }

    private String TryDetermineExpression(String token) {
        String result = null;

        if (EXPRESSIONS.contains(token)) {
            result = token;
        }

        return result;
    }

    private String tryDetermineEqual(String token) {
        String result = null;

        if ("=".equals(token)) {
            result = token;
        }

        return result;
    }

    private String tryDetermineEndLine(String token) {
        String result = null;

        if ("\n".equals(token) || "\r".equals(token)) {
            result = token;
        }

        return result;
    }
}

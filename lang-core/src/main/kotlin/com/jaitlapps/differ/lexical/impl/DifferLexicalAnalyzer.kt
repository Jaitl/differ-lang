package com.jaitlapps.differ.lexical.impl

import com.google.common.collect.ImmutableSet
import com.jaitlapps.differ.lexical.LexicalAnalyzer
import com.jaitlapps.differ.model.*
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.reader.Reader

class DifferLexicalAnalyzer(private val reader: Reader) : LexicalAnalyzer {

    private val EXPRESSIONS = ImmutableSet.builder<String>().add("+").add("-").add("*").add("/").add("^").add("]").add("[").build()
    private var prevToken: Token? = null

    override fun nextToken(): Token {
        val token: Token;

        val word = reader.readNextWord()

        if (word != null) {
            token = determineToken(word)
        } else {
            token = Token(Word(0, 0, "", ""), TokenType.Eof)
        }

        prevToken = token

        return token
    }

    private fun determineToken(word: Word): Token {
        val tokenKeyword = tryDetermineKeyword(word)
        if(tokenKeyword != null) {
            return KeywordToken(word, TokenType.Keyword, tokenKeyword)
        }

        val tokenMethod = tryDetermineMethod(word)
        if (tokenMethod != null) {
            return MethodToken(word, TokenType.Method, tokenMethod)
        }

        val symbolMethod = tryDetermineSymbol(word)
        if (symbolMethod != null) {
            return SymbolToken(word, TokenType.Symbol, symbolMethod)
        }

        return Token(word, TokenType.Unknown)
    }

    private fun tryDetermineKeyword(word: Word): KeywordType? {
        return KeywordType.KEYWORDS[word.capitalizedWord]
    }

    private fun tryDetermineMethod(word: Word): MethodType? {
        return MethodType.METHODS[word.capitalizedWord]
    }

    private fun tryDetermineSymbol(word: Word): SymbolType? {
        return SymbolType.SYMBOLS[word.capitalizedWord]
    }

    // TODO: REFACTOR ME PLEASE!!!!!
    /*private fun DetermineTypeToken(token: Token): Token {

        val tokenInteger = TryDetermineInteger(token.capitalizedString)

        val tokenKeyword = TryDetermineKeyword(token.capitalizedString)
        val tokenReal = TryDetermineReal(token.capitalizedString)
        val tokenVariable = TryDetermineVariable(token.capitalizedString)
        val tokenLabel = TryDetermineLabel(token.capitalizedString)
        val tokenExpression = TryDetermineExpression(token.capitalizedString)
        val tokenEqual = tryDetermineEqual(token.capitalizedString)
        val tokenEndLine = tryDetermineEndLine(token.capitalizedString)

        if ("," == token.capitalizedString) {
            token.tokenType = TokenType.Comma
        } else if (tokenReal != null) {
            token.tokenType = TokenType.Real
            token.valueReal = tokenReal
        } else if (tokenInteger != null) {
            token.tokenType = TokenType.Integer
            token.value = tokenInteger
        } else if (tokenKeyword != null) {
            token.tokenType = TokenType.Keyword

            if (tokenKeyword == "начало") {
                token.keywordType = KeywordType.Begin
            } else if (tokenKeyword == "третье") {
                token.keywordType = KeywordType.Third
            } else if (tokenKeyword == "второе") {
                token.keywordType = KeywordType.Second
            } else if (tokenKeyword == "первое") {
                token.keywordType = KeywordType.First
            } else if (tokenKeyword == "сочетаемое") {
                token.keywordType = KeywordType.Combine
            }
        } else if (tokenVariable != null) {
            token.tokenType = TokenType.Variable
        } else if (tokenLabel != null) {
            token.tokenType = TokenType.Label
        } else if (tokenExpression != null) {
            token.tokenType = TokenType.Expression

            if (tokenExpression == "+") {
                token.expressionType = ExpressionType.Addition
            } else if (tokenExpression == "-") {
                token.expressionType = ExpressionType.Subtraction
            } else if (tokenExpression == "*") {
                token.expressionType = ExpressionType.Multiplication
            } else if (tokenExpression == "/") {
                token.expressionType = ExpressionType.Division
            } else if (tokenExpression == "^") {
                token.expressionType = ExpressionType.Involution
            } else if (tokenExpression == "[") {
                token.expressionType = ExpressionType.OpenBracket
            } else if (tokenExpression == "]") {
                token.expressionType = ExpressionType.CloseBracket
            }
        } else if (tokenEqual != null) {
            token.tokenType = TokenType.Equal
        } else if (tokenEndLine != null) {
            token.tokenType = TokenType.EndLine
        } else {
            token.tokenType = TokenType.Unknown
        }

        return token
    }*/

    private fun TryDetermineExpression(token: String): String? {
        var result: String? = null

        if (EXPRESSIONS.contains(token)) {
            result = token
        }

        return result
    }

    private fun tryDetermineEqual(token: String): String? {
        var result: String? = null

        if ("=" == token) {
            result = token
        }

        return result
    }

    private fun tryDetermineEndLine(token: String): String? {
        var result: String? = null

        if ("\n" == token || "\r" == token) {
            result = token
        }

        return result
    }

    companion object {

        private fun TryDetermineInteger(token: String): Int? {
            try {
                return Integer.valueOf(token)
            } catch (e: NumberFormatException) {
                return null
            }

        }

        // TODO: replace this by regex
        private fun TryDetermineVariable(token: String): String? {
            var isVariable = true

            val tmpCharArr = token.toCharArray()

            if (IsLatinLetter(tmpCharArr[0])) {
                var pos = 1

                while (pos < token.length && isVariable) {
                    if (IsLatinLetterOrDigit(tmpCharArr[pos])) {
                        pos++
                    } else {
                        isVariable = false
                    }
                }
            } else {
                isVariable = false
            }

            if (isVariable) {
                return token
            }

            return null
        }

        private fun IsLatinLetter(ch: Char): Boolean {
            return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z'
                    || ch >= 'а' && ch <= 'я' || ch >= 'А' && ch <= 'Я'

        }

        private fun IsLatinLetterOrDigit(ch: Char): Boolean {
            return IsDigit(ch) || IsLatinLetter(ch)

        }

        private fun IsDigit(ch: Char): Boolean {
            return ch >= '1' && ch <= '9'

        }

        private fun TryDetermineReal(token: String): Double? {
            var result: Double? = null

            if (token.matches("\\d+\\.\\d+".toRegex())) {
                result = java.lang.Double.valueOf(token)
            }

            return result
        }

        private fun TryDetermineLabel(token: String): String? {
            var isLabel = true

            var posEnd = token.length - 1

            val tmpCharArr = token.toCharArray()

            if (tmpCharArr[posEnd] == ':') {
                posEnd--

                while (posEnd > 0 && isLabel) {
                    if (Character.isDigit(tmpCharArr[posEnd])) {
                        posEnd--
                    } else {
                        isLabel = false
                    }
                }
            } else {
                isLabel = false
            }

            if (isLabel)
                return token

            return null
        }
    }
}

package com.jaitlapps.differ.lexical.impl;

import com.jaitlapps.differ.lexical.LexicalAnalyzer;
import com.jaitlapps.differ.model.ExpressionType;
import com.jaitlapps.differ.model.KeywordType;
import com.jaitlapps.differ.model.Token;
import com.jaitlapps.differ.model.TokenType;
import com.jaitlapps.differ.reader.impl.StringReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class DifferLexicalAnalyzerTest {
    @Test
    public void testKeywordBegin() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("Начало"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Keyword);
        assertEquals(token.getKeywordType(), KeywordType.Begin);
    }

    @Test
    public void testTokenTypeUnknown() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("44rrr"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Unknown);
    }

    @Test
    public void testKeywordReal() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("Третье"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Keyword);
        assertEquals(token.getKeywordType(), KeywordType.Third);
    }

    @Test
    public void testKeywordInteger() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("Первое"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Keyword);
        assertEquals(token.getKeywordType(), KeywordType.First);
    }

    @Test
    public void testKeywordBeginEof() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("  Начало     "));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Keyword);
        assertEquals(token.getKeywordType(), KeywordType.Begin);

        token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Eof);

        token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Eof);
    }

    @Test
    public void testInteger125() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("125"));
        final int actual = 125;

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Integer);
        assertEquals(token.getValue(), actual);
    }

    @Test
    public void testVariableAs456r() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("as456r"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Variable);
        assertEquals(token.getCapitalizedString(), "as456r");
    }

    @Test
    public void testVariable4ry44ed() {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("4ry44ed"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Unknown);
    }

    @Test
    public void test_variable_ry4ed()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("ry4#ed"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Unknown);
    }

    @Test
    public void test_label_1234()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("1234:"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Label);
    }

    @Test
    public void test_label_ry4ed()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("ry4ed:"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Unknown);
    }

    @Test
    public void test_label_23458()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("23458"));

        Token token = lexicalAnalyzer.nextToken();

        assertNotEquals(token.getTokenType(), TokenType.Label);
    }

    @Test
    public void test_label_negative_23458()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("-23458"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getExpressionType(), ExpressionType.Subtraction);

        token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Integer);
    }

    @Test
    public void test_expression_add()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("+"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Expression);
        assertEquals(token.getExpressionType(), ExpressionType.Addition);
    }

    @Test
    public void test_expression_sub()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("-"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Expression);
        assertEquals(token.getExpressionType(), ExpressionType.Subtraction);
    }

    @Test
    public void test_expression_multi()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("*"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Expression);
        assertEquals(token.getExpressionType(), ExpressionType.Multiplication);
    }

    @Test
    public void test_expression_div()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("/"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Expression);
        assertEquals(token.getExpressionType(), ExpressionType.Division);
    }

    @Test
    public void test_expression_inv()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("^"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Expression);
        assertEquals(token.getExpressionType(), ExpressionType.Involution);
    }

    @Test
    public void test_equal()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("="));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Equal);
    }

    @Test
    public void test_open_bracket()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("["));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getExpressionType(), ExpressionType.OpenBracket);
    }

    @Test
    public void test_close_bracket()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("]"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getExpressionType(), ExpressionType.CloseBracket);
    }

    @Test
    public void test_real_1()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("43.332"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Real);
    }

    @Test
    public void test_real_2()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader("43."));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Unknown);
    }

    @Test
    public void test_real_3()
    {
        LexicalAnalyzer lexicalAnalyzer = new DifferLexicalAnalyzer(new StringReader(".4534"));

        Token token = lexicalAnalyzer.nextToken();

        assertEquals(token.getTokenType(), TokenType.Unknown);
    }
}
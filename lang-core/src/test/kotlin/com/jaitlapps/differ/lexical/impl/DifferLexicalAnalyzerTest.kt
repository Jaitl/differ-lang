package com.jaitlapps.differ.lexical.impl

import com.jaitlapps.differ.lexical.LexicalAnalyzer
import com.jaitlapps.differ.model.ExpressionType
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.Token
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.reader.impl.StringReader
import org.junit.Test

import org.junit.Assert.*

class DifferLexicalAnalyzerTest {
    @Test
    fun testKeywordBegin() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Начало"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Keyword)
        assertEquals(token.keywordType, KeywordType.Begin)
    }

    @Test
    fun testTokenTypeUnknown() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("44rrr"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Unknown)
    }

    @Test
    fun testKeywordReal() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Третье"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Keyword)
        assertEquals(token.keywordType, KeywordType.Third)
    }

    @Test
    fun testKeywordInteger() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Первое"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Keyword)
        assertEquals(token.keywordType, KeywordType.First)
    }

    @Test
    fun testKeywordBeginEof() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("  Начало     "))

        var token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Keyword)
        assertEquals(token.keywordType, KeywordType.Begin)

        token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Eof)

        token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Eof)
    }

    @Test
    fun testInteger125() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("125"))
        val actual = 125

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Integer)
        assertEquals(token.value.toLong(), actual.toLong())
    }

    @Test
    fun testVariableAs456r() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("as456r"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Variable)
        assertEquals(token.capitalizedString, "as456r")
    }

    @Test
    fun testVariable4ry44ed() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("4ry44ed"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Unknown)
    }

    @Test
    fun test_variable_ry4ed() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("ry4#ed"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Unknown)
    }

    @Test
    fun test_label_1234() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("1234:"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Label)
    }

    @Test
    fun test_label_ry4ed() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("ry4ed:"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Unknown)
    }

    @Test
    fun test_label_23458() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("23458"))

        val token = lexicalAnalyzer.nextToken()

        assertNotEquals(token.tokenType, TokenType.Label)
    }

    @Test
    fun test_label_negative_23458() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("-23458"))

        var token = lexicalAnalyzer.nextToken()

        assertEquals(token.expressionType, ExpressionType.Subtraction)

        token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Integer)
    }

    @Test
    fun test_expression_add() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("+"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Expression)
        assertEquals(token.expressionType, ExpressionType.Addition)
    }

    @Test
    fun test_expression_sub() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("-"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Expression)
        assertEquals(token.expressionType, ExpressionType.Subtraction)
    }

    @Test
    fun test_expression_multi() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("*"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Expression)
        assertEquals(token.expressionType, ExpressionType.Multiplication)
    }

    @Test
    fun test_expression_div() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("/"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Expression)
        assertEquals(token.expressionType, ExpressionType.Division)
    }

    @Test
    fun test_expression_inv() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("^"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Expression)
        assertEquals(token.expressionType, ExpressionType.Involution)
    }

    @Test
    fun test_equal() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("="))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Equal)
    }

    @Test
    fun test_open_bracket() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("["))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.expressionType, ExpressionType.OpenBracket)
    }

    @Test
    fun test_close_bracket() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("]"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.expressionType, ExpressionType.CloseBracket)
    }

    @Test
    fun test_real_1() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("43.332"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Real)
    }

    @Test
    fun test_real_2() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("43."))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Unknown)
    }

    @Test
    fun test_real_3() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader(".4534"))

        val token = lexicalAnalyzer.nextToken()

        assertEquals(token.tokenType, TokenType.Unknown)
    }
}
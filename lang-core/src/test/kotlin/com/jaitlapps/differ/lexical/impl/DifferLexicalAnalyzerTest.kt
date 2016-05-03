package com.jaitlapps.differ.lexical.impl

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.NumberToken
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.reader.impl.StringReader
import org.junit.Test
import kotlin.test.assertEquals

class DifferLexicalAnalyzerTest {
    @Test
    fun testTokenTypeUnknown(){
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("йапрограмко"))
        val token = lexicalAnalyzer.nextToken()

        assertEquals(TokenType.Unknown, token.tokenType)
    }

    @Test
    fun testKeywordProgram() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Программа"))

        val token:KeywordToken = lexicalAnalyzer.nextToken() as KeywordToken

        assertEquals(TokenType.Keyword, token.tokenType)
        assertEquals(KeywordType.Program, token.keywordType)

        val nextToken = lexicalAnalyzer.nextToken()
        assertEquals(TokenType.Eof, nextToken.tokenType)
    }

    @Test
    fun testKeyWordMethod() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Метод Эйлера;"))

        val token:KeywordToken = lexicalAnalyzer.nextToken() as KeywordToken

        assertEquals(TokenType.Keyword, token.tokenType)
        assertEquals(KeywordType.Method, token.keywordType)

        val methodToken = lexicalAnalyzer.nextToken() as MethodToken
        assertEquals(TokenType.Method, methodToken.tokenType)
        assertEquals(MethodType.Eiler, methodToken.methodType)

        val symbolToken = lexicalAnalyzer.nextToken() as SymbolToken
        assertEquals(TokenType.Symbol, symbolToken.tokenType)
        assertEquals(SymbolType.Semicolon, symbolToken.symbolType)
    }

    @Test
    fun testKeyWordDefinition() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Коэффициенты a = 4;"))

        val token:KeywordToken = lexicalAnalyzer.nextToken() as KeywordToken

        assertEquals(TokenType.Keyword, token.tokenType)
        assertEquals(KeywordType.Coefficient, token.keywordType)

        val coefficientToken = lexicalAnalyzer.nextToken()

        assertEquals(TokenType.Coefficient, coefficientToken.tokenType)
        assertEquals("a", coefficientToken.word.word)

        val equalToken = lexicalAnalyzer.nextToken() as SymbolToken

        assertEquals(TokenType.Symbol, equalToken.tokenType)
        assertEquals(SymbolType.Equal, equalToken.symbolType)

        val numberToken = lexicalAnalyzer.nextToken() as NumberToken

        assertEquals(TokenType.Integer, numberToken.tokenType)
        assertEquals(4, numberToken.number.toInt())

        val symbolToken = lexicalAnalyzer.nextToken() as SymbolToken
        assertEquals(TokenType.Symbol, symbolToken.tokenType)
        assertEquals(SymbolType.Semicolon, symbolToken.symbolType)
    }

    @Test
    fun testNumber() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("12334 323.432"))

        val tokenInteger = lexicalAnalyzer.nextToken() as NumberToken

        assertEquals(TokenType.Integer, tokenInteger.tokenType)
        assertEquals(12334, tokenInteger.number.toInt())

        val tokenDouble = lexicalAnalyzer.nextToken() as NumberToken

        assertEquals(TokenType.Double, tokenDouble.tokenType)
        assertEquals(323.432.toDouble(), tokenDouble.number.toDouble())
    }

    @Test
    fun testKeywordInterval() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Интервал 0.6, 50;"))

        val token:KeywordToken = lexicalAnalyzer.nextToken() as KeywordToken

        assertEquals(TokenType.Keyword, token.tokenType)
        assertEquals(KeywordType.Interval, token.keywordType)

        val tokenDouble = lexicalAnalyzer.nextToken() as NumberToken

        assertEquals(TokenType.Double, tokenDouble.tokenType)
        assertEquals(0.6.toDouble(), tokenDouble.number.toDouble())

        val tokenComma = lexicalAnalyzer.nextToken() as SymbolToken

        assertEquals(TokenType.Symbol, tokenComma.tokenType)
        assertEquals(SymbolType.Comma, tokenComma.symbolType)

        val tokenInteger = lexicalAnalyzer.nextToken() as NumberToken

        assertEquals(TokenType.Integer, tokenInteger.tokenType)
        assertEquals(50, tokenInteger.number.toInt())

        val symbolToken = lexicalAnalyzer.nextToken() as SymbolToken
        assertEquals(TokenType.Symbol, symbolToken.tokenType)
        assertEquals(SymbolType.Semicolon, symbolToken.symbolType)
    }

    @Test
    fun testKeywordStep() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Шаг 0.6;"))

        val token:KeywordToken = lexicalAnalyzer.nextToken() as KeywordToken

        assertEquals(TokenType.Keyword, token.tokenType)
        assertEquals(KeywordType.Step, token.keywordType)

        val tokenDouble = lexicalAnalyzer.nextToken() as NumberToken

        assertEquals(TokenType.Double, tokenDouble.tokenType)
        assertEquals(0.6.toDouble(), tokenDouble.number.toDouble())

        val symbolToken = lexicalAnalyzer.nextToken() as SymbolToken
        assertEquals(TokenType.Symbol, symbolToken.tokenType)
        assertEquals(SymbolType.Semicolon, symbolToken.symbolType)
    }

    @Test
    fun testKeywordValue() {
        val lexicalAnalyzer = DifferLexicalAnalyzer(StringReader("Значения x11 = 10;"))

        val token:KeywordToken = lexicalAnalyzer.nextToken() as KeywordToken

        assertEquals(TokenType.Keyword, token.tokenType)
        assertEquals(KeywordType.Value, token.keywordType)

        val tokenXk = lexicalAnalyzer.nextToken()
        assertEquals(TokenType.Xk, tokenXk.tokenType)
        assertEquals("x11", tokenXk.word.word)

        val equalToken = lexicalAnalyzer.nextToken() as SymbolToken

        assertEquals(TokenType.Symbol, equalToken.tokenType)
        assertEquals(SymbolType.Equal, equalToken.symbolType)

        val numberToken = lexicalAnalyzer.nextToken() as NumberToken

        assertEquals(TokenType.Integer, numberToken.tokenType)
        assertEquals(10, numberToken.number.toInt())

        val symbolToken = lexicalAnalyzer.nextToken() as SymbolToken
        assertEquals(TokenType.Symbol, symbolToken.tokenType)
        assertEquals(SymbolType.Semicolon, symbolToken.symbolType)
    }
}
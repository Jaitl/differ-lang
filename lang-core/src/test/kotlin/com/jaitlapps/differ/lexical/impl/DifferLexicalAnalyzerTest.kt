package com.jaitlapps.differ.lexical.impl

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.reader.impl.StringReader
import org.junit.Assert.assertEquals
import org.junit.Test

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
}
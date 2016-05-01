package com.jaitlapps.differ.lexical.impl

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.TokenType
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

        var token = lexicalAnalyzer.nextToken()

        assertEquals(TokenType.Keyword, token.tokenType)
        assertEquals(KeywordType.Program, token.keywordType)

        token = lexicalAnalyzer.nextToken()
        assertEquals(TokenType.Eof, token.tokenType)
    }
}
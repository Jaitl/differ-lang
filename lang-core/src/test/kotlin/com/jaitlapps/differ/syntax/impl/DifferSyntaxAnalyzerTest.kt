package com.jaitlapps.differ.syntax.impl

import com.jaitlapps.differ.lexical.impl.DifferLexicalAnalyzer
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.reader.impl.StringReader
import com.jaitlapps.differ.syntax.SyntaxAnalyzer
import org.junit.Test
import kotlin.test.*

class DifferSyntaxAnalyzerTest {
    @Test
    fun testMethodTree() {
        val syntaxAnalyzer = createSyntaxAnalyzer("Программа Метод Эйлера;")

        val tree = syntaxAnalyzer.generateSyntaxTree();

        val programToken = tree.token as KeywordToken
        assertEquals(TokenType.Keyword, programToken.tokenType)
        assertEquals(KeywordType.Program, programToken.keywordType)

        assertEquals(1, tree.childs.count())

        val methodTree = tree.childs[0]
        val methodToken = methodTree.token as KeywordToken

        assertEquals(TokenType.Keyword, methodToken.tokenType)
        assertEquals(KeywordType.Method, methodToken.keywordType)

        assertEquals(1, methodTree.childs.count())

        val eilerTree = methodTree.childs[0]
        val eilerToken = eilerTree.token as MethodToken

        assertEquals(TokenType.Method, eilerToken.tokenType)
        assertEquals(MethodType.Eiler, eilerToken.methodType)

        assertEquals(0, eilerTree.childs.count())
    }

    fun createSyntaxAnalyzer(code: String): SyntaxAnalyzer {
        return DifferSyntaxAnalyzer(DifferLexicalAnalyzer(StringReader(code)))
    }
}
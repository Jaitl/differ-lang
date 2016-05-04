package com.jaitlapps.differ.syntax.impl

import com.jaitlapps.differ.factory.DifferFactory
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.NumberToken
import com.jaitlapps.differ.syntax.rule.DifferSyntaxRulesFactory
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class DifferSyntaxAnalyzerTest {
    @Test
    fun testMethod(){
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Метод Эйлера;",
                DifferSyntaxRulesFactory.createDifferMethodRules())

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

    @Test
    fun testCoefficient() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Коэффициенты a = 10; b = 50;",
                DifferSyntaxRulesFactory.createDifferCoefficientRules())

        val tree = syntaxAnalyzer.generateSyntaxTree();

        val programToken = tree.token as KeywordToken
        assertEquals(TokenType.Keyword, programToken.tokenType)
        assertEquals(KeywordType.Program, programToken.keywordType)

        assertEquals(1, tree.childs.count())

        val coeffNodeTree = tree.childs[0]
        val coeffToken = coeffNodeTree.token as KeywordToken

        assertEquals(TokenType.Keyword, coeffToken.tokenType)
        assertEquals(KeywordType.Coefficient, coeffToken.keywordType)

        assertEquals(2, coeffNodeTree.childs.count())

        val coeffATree = coeffNodeTree.childs.findLast { tree -> tree.token.word.word == "a" }!!
        val coeffAToken = coeffATree.token

        assertEquals(TokenType.Coefficient, coeffAToken.tokenType)
        Assert.assertEquals("a", coeffAToken.word.word)

        assertEquals(1, coeffATree.childs.count())

        val coeffAIntTree = coeffATree.childs[0]
        val coeffAIntToken = coeffAIntTree.token as NumberToken

        assertEquals(TokenType.Integer, coeffAIntToken.tokenType)
        assertEquals(10, coeffAIntToken.number.toInt())

        assertEquals(0, coeffAIntTree.childs.count())

        val coeffBTree = coeffNodeTree.childs.findLast { tree -> tree.token.word.word == "b" }!!
        val coeffBToken = coeffBTree.token

        assertEquals(TokenType.Coefficient, coeffBToken.tokenType)
        Assert.assertEquals("b", coeffBToken.word.word)

        assertEquals(1, coeffBTree.childs.count())

        val coeffBIntTree = coeffBTree.childs[0]
        val coeffBIntToken = coeffBIntTree.token as NumberToken

        assertEquals(TokenType.Integer, coeffBIntToken.tokenType)
        assertEquals(50, coeffBIntToken.number.toInt())

        assertEquals(0, coeffBIntTree.childs.count())
    }
}
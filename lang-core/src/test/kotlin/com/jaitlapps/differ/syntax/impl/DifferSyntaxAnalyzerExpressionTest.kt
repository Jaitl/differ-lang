package com.jaitlapps.differ.syntax.impl

import com.jaitlapps.differ.factory.DifferFactory
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.NumberToken
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.syntax.rule.ExpressionSyntaxRuleFactory
import org.junit.Test
import kotlin.test.assertEquals

class DifferSyntaxAnalyzerExpressionTest {
    @Test
    fun testSimpleExpression() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("= 3 * 2 + 5;",
                ExpressionSyntaxRuleFactory.createTestExpressionRules())

        val tree = syntaxAnalyzer.generateSyntaxTree()

        assertEquals(1, tree.childs.count())

        val integ = tree.childs[0]
        val integToken = integ.token as NumberToken

        assertEquals(1, integ.childs.count())
        assertEquals(TokenType.Integer, integToken.tokenType)
        assertEquals(3, integToken.number.toInt())
    }

    @Test
    fun testHardExpression() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("= sin(4^a) + (-x1) + tg 4 / (54*x55*(-r+2));",
                ExpressionSyntaxRuleFactory.createTestExpressionRules())

        val tree = syntaxAnalyzer.generateSyntaxTree()

        assertEquals(1, tree.childs.count())

        val sinTree = tree.childs[0]
        val sinToken = sinTree.token as SymbolToken

        assertEquals(1, sinTree.childs.count())
        assertEquals(TokenType.Symbol, sinToken.tokenType)
        assertEquals(SymbolType.Sin, sinToken.symbolType)
    }
}
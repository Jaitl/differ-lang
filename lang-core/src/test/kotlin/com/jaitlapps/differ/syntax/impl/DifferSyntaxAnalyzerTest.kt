package com.jaitlapps.differ.syntax.impl

import com.jaitlapps.differ.factory.DifferFactory
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.NumberToken
import com.jaitlapps.differ.model.token.SymbolToken
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

    @Test
    fun testInterval() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Интервал 0, 50;",
                DifferSyntaxRulesFactory.createDifferIntervalRules())

        val tree = syntaxAnalyzer.generateSyntaxTree();

        val programToken = tree.token as KeywordToken
        assertEquals(TokenType.Keyword, programToken.tokenType)
        assertEquals(KeywordType.Program, programToken.keywordType)

        assertEquals(1, tree.childs.count())

        val intervalTree = tree.childs[0]
        val intervalToken = intervalTree.token as KeywordToken

        assertEquals(TokenType.Keyword, intervalToken.tokenType)
        assertEquals(KeywordType.Interval, intervalToken.keywordType)

        assertEquals(2, intervalTree.childs.count())

        val numberTree0 = intervalTree.childs.first { tree -> val token = tree.token; token is NumberToken && token.number.toInt() == 0 }
        val numberToken0 = numberTree0.token as NumberToken

        assertEquals(TokenType.Integer, numberToken0.tokenType)
        assertEquals(0, numberToken0.number.toInt())

        assertEquals(0, numberTree0.childs.count())

        val numberTree50 = intervalTree.childs.first { tree -> val token = tree.token; token is NumberToken && token.number.toInt() == 50 }
        val numberToken50 = numberTree50.token as NumberToken

        assertEquals(TokenType.Integer, numberToken50.tokenType)
        assertEquals(50, numberToken50.number.toInt())

        assertEquals(0, numberTree50.childs.count())
    }

    @Test
    fun testStep() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Шаг 0.6;",
                DifferSyntaxRulesFactory.createDifferStepRules())

        val tree = syntaxAnalyzer.generateSyntaxTree();

        val programToken = tree.token as KeywordToken
        assertEquals(TokenType.Keyword, programToken.tokenType)
        assertEquals(KeywordType.Program, programToken.keywordType)

        assertEquals(1, tree.childs.count())

        val stepTree = tree.childs[0]
        val stepToken = stepTree.token as KeywordToken

        assertEquals(TokenType.Keyword, stepToken.tokenType)
        assertEquals(KeywordType.Step, stepToken.keywordType)

        assertEquals(1, stepTree.childs.count())

        val numberTree = stepTree.childs[0]
        val numberToken = numberTree.token as NumberToken

        assertEquals(TokenType.Double, numberToken.tokenType)
        assertEquals(0.6.toDouble(), numberToken.number.toDouble())

        assertEquals(0, numberTree.childs.count())
    }

    @Test
    fun testValue() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Значения x11 = 10; x22 = 43;",
                DifferSyntaxRulesFactory.createDifferValueRules())

        val tree = syntaxAnalyzer.generateSyntaxTree();

        val programToken = tree.token as KeywordToken
        assertEquals(TokenType.Keyword, programToken.tokenType)
        assertEquals(KeywordType.Program, programToken.keywordType)

        assertEquals(1, tree.childs.count())

        val valueTree = tree.childs[0]
        val valueToken = valueTree.token as KeywordToken

        assertEquals(TokenType.Keyword, valueToken.tokenType)
        assertEquals(KeywordType.Value, valueToken.keywordType)

        assertEquals(2, valueTree.childs.count())

        val x11Tree = valueTree.childs.first { tree -> tree.token.word.word == "x11" }
        val x11Token = x11Tree.token

        assertEquals(TokenType.Xk, x11Token.tokenType)
        assertEquals("x11", x11Token.word.word)

        assertEquals(1, x11Tree.childs.count())

        val x11valTree = x11Tree.childs[0]
        val x11valToken = x11valTree.token as NumberToken

        assertEquals(TokenType.Integer, x11valToken.tokenType)
        assertEquals(10, x11valToken.number.toInt())

        assertEquals(0, x11valTree.childs.count())

        val x22Tree = valueTree.childs.first { tree -> tree.token.word.word == "x22" }
        val x22Token = x22Tree.token

        assertEquals(TokenType.Xk, x22Token.tokenType)
        assertEquals("x22", x22Token.word.word)

        assertEquals(1, x22Tree.childs.count())

        val x22valTree = x22Tree.childs[0]
        val x22valToken = x22valTree.token as NumberToken

        assertEquals(TokenType.Integer, x22valToken.tokenType)
        assertEquals(43, x22valToken.number.toInt())

        assertEquals(0, x22valTree.childs.count())
    }

    @Test
    fun testEquation() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Уравнения dxdt1 = 2+3; dxdt2 = 5+6;",
                DifferSyntaxRulesFactory.createDifferEquationRules())

        val tree = syntaxAnalyzer.generateSyntaxTree();

        val programToken = tree.token as KeywordToken
        assertEquals(TokenType.Keyword, programToken.tokenType)
        assertEquals(KeywordType.Program, programToken.keywordType)

        assertEquals(1, tree.childs.count())

        val equationTree = tree.childs[0]
        val equationToken = equationTree.token as KeywordToken

        assertEquals(TokenType.Keyword, equationToken.tokenType)
        assertEquals(KeywordType.Equation, equationToken.keywordType)

        assertEquals(2, equationTree.childs.count())

        val dxdt1Tree = equationTree.childs.first { tree -> tree.token.word.word == "dxdt1" }
        val dxdt1Token = dxdt1Tree.token

        assertEquals(TokenType.Dxdtk, dxdt1Token.tokenType)
        assertEquals("dxdt1", dxdt1Token.word.word)

        assertEquals(1, dxdt1Tree.childs.count())

        val dxdt1val1Tree = dxdt1Tree.childs[0]
        val dxdt1val1Token = dxdt1val1Tree.token as NumberToken

        assertEquals(TokenType.Integer, dxdt1val1Token.tokenType)
        assertEquals(2, dxdt1val1Token.number.toInt())

        assertEquals(1, dxdt1val1Tree.childs.count())

        val dxdt1sing1Tree = dxdt1val1Tree.childs[0]
        val dxdt1sign1Token = dxdt1sing1Tree.token as SymbolToken

        assertEquals(SymbolType.Addition, dxdt1sign1Token.symbolType)

        assertEquals(1, dxdt1sing1Tree.childs.count())

        val dxdt1val2Tree = dxdt1sing1Tree.childs[0]
        val dxdt1val2Token = dxdt1val2Tree.token as NumberToken

        assertEquals(TokenType.Integer, dxdt1val2Token.tokenType)
        assertEquals(3, dxdt1val2Token.number.toInt())

        assertEquals(0, dxdt1val2Tree.childs.count())

        val dxdt2Tree = equationTree.childs.first { tree -> tree.token.word.word == "dxdt2" }
        val dxdt2Token = dxdt2Tree.token

        assertEquals(TokenType.Dxdtk, dxdt2Token.tokenType)
        assertEquals("dxdt2", dxdt2Token.word.word)

        assertEquals(1, dxdt2Tree.childs.count())

        val dxdt2val1Tree = dxdt2Tree.childs[0]
        val dxdt2val1Token = dxdt2val1Tree.token as NumberToken

        assertEquals(TokenType.Integer, dxdt2val1Token.tokenType)
        assertEquals(5, dxdt2val1Token.number.toInt())

        assertEquals(1, dxdt2val1Tree.childs.count())

        val dxdt2sing1Tree = dxdt2val1Tree.childs[0]
        val dxdt2sign1Token = dxdt2sing1Tree.token as SymbolToken

        assertEquals(SymbolType.Addition, dxdt2sign1Token.symbolType)

        assertEquals(1, dxdt2sing1Tree.childs.count())

        val dxdt2val2Tree = dxdt2sing1Tree.childs[0]
        val dxdt2val2Token = dxdt2val2Tree.token as NumberToken

        assertEquals(TokenType.Integer, dxdt2val2Token.tokenType)
        assertEquals(6, dxdt2val2Token.number.toInt())

        assertEquals(0, dxdt2val2Tree.childs.count())
    }
}
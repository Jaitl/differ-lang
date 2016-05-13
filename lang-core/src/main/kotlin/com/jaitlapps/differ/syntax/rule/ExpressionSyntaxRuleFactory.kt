package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.model.token.Token

object ExpressionSyntaxRuleFactory {
    /**
     * Integer, Double, Coefficient, Xk
     */
    fun createGroupOne(): SyntaxRule {
        return DifferSyntaxRule(fun(t: Token): Boolean {
            return t.tokenType == TokenType.Integer
                    || t.tokenType == TokenType.Double
                    || t.tokenType == TokenType.Coefficient
                    || t.tokenType == TokenType.Xk
        }, "", TreeSavePosition.CurrentTree)
    }

    /**
     * -, +, *, /, ^
     */
    fun createGroupTwo(): SyntaxRule {
        return DifferSyntaxRule(fun(t: Token): Boolean {
            return t is SymbolToken && (
                    t.symbolType == SymbolType.Subtraction
                    || t.symbolType == SymbolType.Addition
                    || t.symbolType == SymbolType.Multiplication
                    || t.symbolType == SymbolType.Division
                    || t.symbolType == SymbolType.Involution
                    )}, "", TreeSavePosition.CurrentTree)
    }

    /**
     * -, (, sin, cos, tg
     */
    fun createGroupThree(): SyntaxRule {
        return DifferSyntaxRule(fun(t: Token): Boolean {
            return t is SymbolToken && (
                    t.symbolType == SymbolType.Subtraction
                            || t.symbolType == SymbolType.OpenBracket
                            || t.symbolType == SymbolType.Sin
                            || t.symbolType == SymbolType.Cos
                            || t.symbolType == SymbolType.Tg
                    )}, "", TreeSavePosition.CurrentTree)
    }

    /**
     * )
     */
    fun createGroupFour(): SyntaxRule {
        return DifferSyntaxRule({ t -> t is SymbolToken && t.symbolType == SymbolType.CloseBracket},
                "", TreeSavePosition.CurrentTree)
    }

    fun createExpressionRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val g1 = createGroupOne()
        val g2 = createGroupTwo()
        val g3 = createGroupThree()
        val g4 = createGroupFour()

        val c0 = ComposeDifferSyntaxRule("После знака \"=\" должно следовать целое, вещественное, коэффициент, xk, открывающая скобка, знак минуса или функция")
        startRule?.setNextRule(c0)

        c0.setNextRule(g1)
        c0.setNextRule(g3)

        val c1 = ComposeDifferSyntaxRule("После целого, вещественного, коэффициента или xk должен следовать знак математической операции, закрывающая скобка или знак \";\"")
        g1.setNextRule(c1)

        c1.setNextRule(g2)
        c1.setNextRule(g4)
        endRule?.run { c1.setNextRule(endRule) }

        val c2 = ComposeDifferSyntaxRule("После знака математической операции должно следовать целое, вещественное, коэффициент, xk, открывающая скобка, знак минуса или функция")
        g2.setNextRule(c2)

        c2.setNextRule(g1)
        c2.setNextRule(g3)

        val c3 = ComposeDifferSyntaxRule("После открывающей скобки, знака минуса или функции должено следовать следовать целое, вещественное, коэффициент, xk, открывающая скобка, знак минуса или функция")
        g3.setNextRule(c3)

        c3.setNextRule(g3)
        c3.setNextRule(g1)

        val c4 = ComposeDifferSyntaxRule("После закрывающей скобки должен следовать знак математической операции, закрывающая скобка или знак \";\"")
        g4.setNextRule(c4)

        c4.setNextRule(g2)
        c4.setNextRule(g4)
        endRule?.run { c4.setNextRule(endRule) }

        return c0;
    }

    fun createTestExpressionRules(): SyntaxRule {
        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                "", TreeSavePosition.None)

        val semiCoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, "", TreeSavePosition.None)

        createExpressionRules(equalRule, semiCoefficientRule)

        return equalRule
    }
}
package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.NumberToken
import com.jaitlapps.differ.model.token.SymbolToken

object DifferSyntaxRulesFactory {
    fun createDifferFullRules() : SyntaxRule {
        val programRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Program},
                {"Программа должна начинаться с оператора \"Программа\""}, TreeSavePosition.None)

        val coefficient = createDifferCoefficientRules()

        val methodRule = createDifferMethodRules(startRule = programRule, endRule = coefficient)

        return programRule
    }

    fun createDifferMethodRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val methodRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Method},
                {"За оператором \"Программа\" должен следовать оператор \"Метод\""}, TreeSavePosition.RootTree)

        startRule?.setNextRule(methodRule)

        val eilerRule = DifferSyntaxRule({token -> token is MethodToken},
                {"За оператором \"Метод\" должно следовать название метода"}, TreeSavePosition.NodeTree)
        methodRule.setNextRule(eilerRule)

        val endMetdodRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon},
                {"Оператор \"Метод\" должен заканчиваться символом \";\""}, TreeSavePosition.None)

        eilerRule.setNextRule(endMetdodRule)

        endRule?.let { endMetdodRule.setNextRule(endRule) }

        return methodRule
    }

    fun createDifferCoefficientRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val coefficientRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Coefficient},
                {"За оператором \"Метод\" должен следовать оператор \"Коеффециенты\""}, TreeSavePosition.RootTree)

        val coefNodeRule = DifferSyntaxRule({token -> token.tokenType == TokenType.Coefficient},
                {"За оператором \"Коеффециенты\" должен следовать оператор \"коев\""}, TreeSavePosition.NodeTree)

        coefficientRule.setNextRule(coefNodeRule)

        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                {"За оператором \"Коеффециенты\" должен следовать оператор \"=\""}, TreeSavePosition.None)

        coefNodeRule.setNextRule(equalRule)

        val numberRule = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                {"За оператором \"=\" должено следовать целое или вещественное число"}, TreeSavePosition.CurrentTree)

        equalRule.setNextRule(numberRule)

        val semiСoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon},
                {"Оператор \"Коеффециенты\" должен заканчиваться символом \";\""}, TreeSavePosition.None)

        numberRule.setNextRule(semiСoefficientRule)

        val endСoefficientRule: ComposeDifferSyntaxRule = ComposeDifferSyntaxRule{"Ошибочка"}

        semiСoefficientRule.setNextRule(endСoefficientRule)

        endСoefficientRule.setNextRule(coefNodeRule)

        endRule?.run { endСoefficientRule.setNextRule(endRule) }

        return coefficientRule
    }
}
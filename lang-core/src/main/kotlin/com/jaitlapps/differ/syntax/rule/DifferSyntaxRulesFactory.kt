package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.NumberToken
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.syntax.error.ErrorMessageGenerator

object DifferSyntaxRulesFactory {
    fun createDifferFullRules() : SyntaxRule {
        val programRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Program},
                "Программа должна начинаться с оператора \"Программа\"", TreeSavePosition.None)

        val coefficient = createDifferCoefficientRules()

        val methodRule = createDifferMethodRules(startRule = programRule, endRule = coefficient)

        return programRule
    }

    fun createDifferMethodRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val methodRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Method},
                ErrorMessageGenerator.generateNextObject("Программа", "Метод"), TreeSavePosition.RootTree)

        startRule?.setNextRule(methodRule)

        val eilerRule = DifferSyntaxRule({token -> token is MethodToken},
                ErrorMessageGenerator.generateNextMultipleObject("Метод", listOf("Эйлера", "РунгеКутты")), TreeSavePosition.NodeTree)
        methodRule.setNextRule(eilerRule)

        val endMetdodRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon},
                ErrorMessageGenerator.generateEndSymbolObject("Метод", ";"), TreeSavePosition.None)

        eilerRule.setNextRule(endMetdodRule)

        endRule?.let { endMetdodRule.setNextRule(endRule) }

        return methodRule
    }

    fun createDifferCoefficientRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val coefficientRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Coefficient},
               ErrorMessageGenerator.generateNextObject("Метод", "Коеффециенты"), TreeSavePosition.RootTree)

        val coefNodeRule = DifferSyntaxRule({token -> token.tokenType == TokenType.Coefficient},
                ErrorMessageGenerator.generateNextObject("Коеффециенты", "Коеффециент"), TreeSavePosition.NodeTree)

        coefficientRule.setNextRule(coefNodeRule)

        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                ErrorMessageGenerator.generateNextObject("Коеффециент", "="), TreeSavePosition.None)

        coefNodeRule.setNextRule(equalRule)

        val numberRule = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                ErrorMessageGenerator.generateNextMultipleObject("=", listOf("Целое", "Вещественное")), TreeSavePosition.CurrentTree)

        equalRule.setNextRule(numberRule)

        val semiСoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon},
                ErrorMessageGenerator.generateEndSymbolObject("Коеффециенты", ";"), TreeSavePosition.None)

        numberRule.setNextRule(semiСoefficientRule)

        val endСoefficientRule: ComposeDifferSyntaxRule = ComposeDifferSyntaxRule("Ошибочка")

        semiСoefficientRule.setNextRule(endСoefficientRule)

        endСoefficientRule.setNextRule(coefNodeRule)

        if (endRule != null) {
            endСoefficientRule.setNextRule(endRule)
        } else {
            endСoefficientRule.setNextRule(EofRule)
        }

        return coefficientRule
    }
}
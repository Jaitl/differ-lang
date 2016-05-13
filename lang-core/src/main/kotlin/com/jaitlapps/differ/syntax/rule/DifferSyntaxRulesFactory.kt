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

        val endProgramRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.EndProgram},
                "После оператора \"Конец\" не должно быть других операторов", TreeSavePosition.None)

        val equation = createDifferEquationRules(endRule = endProgramRule)

        val value = createDifferValueRules(endRule = equation)

        val step = createDifferStepRules(endRule = value)

        val interval = createDifferIntervalRules(endRule = step)

        val coefficient = createDifferCoefficientRules(endRule = interval)

        createDifferMethodRules(startRule = programRule, endRule = coefficient)

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

        val semiCoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, ErrorMessageGenerator.generateEndSymbolObject("Коеффециенты", ";"), TreeSavePosition.None)

        numberRule.setNextRule(semiCoefficientRule)

        val endCoefficientRule = ComposeDifferSyntaxRule("После оператора \";\" должен следовать следующий коэффициент, либо оператор \"Интервал\"")

        semiCoefficientRule.setNextRule(endCoefficientRule)

        endCoefficientRule.setNextRule(coefNodeRule)

        if (endRule != null) {
            endCoefficientRule.setNextRule(endRule)
        } else {
            endCoefficientRule.setNextRule(EofRule)
        }

        return coefficientRule
    }

    fun createDifferIntervalRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val intervalRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Interval},
                ErrorMessageGenerator.generateNextObject("Коеффециенты", "Интервал"), TreeSavePosition.RootTree)

        startRule?.setNextRule(intervalRule)

        val numberRuleOne = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                ErrorMessageGenerator.generateNextMultipleObject("Интервал", listOf("Целое", "Вещественное")), TreeSavePosition.NodeTree)

        intervalRule.setNextRule(numberRuleOne)

        val commaRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Comma},
                ErrorMessageGenerator.generateNextObject("Число", ","), TreeSavePosition.None)

        numberRuleOne.setNextRule(commaRule)

        val numberRuleTwo = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                ErrorMessageGenerator.generateNextMultipleObject(",", listOf("Целое", "Вещественное")), TreeSavePosition.NodeTree)

        commaRule.setNextRule(numberRuleTwo)

        val semiCoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, ErrorMessageGenerator.generateEndSymbolObject("Число", ";"), TreeSavePosition.None)

        numberRuleTwo.setNextRule(semiCoefficientRule)

        endRule?.let { semiCoefficientRule.setNextRule(endRule) }

        return intervalRule
    }

    fun createDifferStepRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val stepRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Step},
                ErrorMessageGenerator.generateNextObject("Интервал", "Шаг"), TreeSavePosition.RootTree)

        startRule?.setNextRule(stepRule)

        val numberRule = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                ErrorMessageGenerator.generateNextMultipleObject("Шаг", listOf("Целое", "Вещественное")), TreeSavePosition.NodeTree)

        stepRule.setNextRule(numberRule)

        val semiCoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, ErrorMessageGenerator.generateEndSymbolObject("Число", ";"), TreeSavePosition.None)

        numberRule.setNextRule(semiCoefficientRule)

        endRule?.let { semiCoefficientRule.setNextRule(endRule) }

        return stepRule
    }

    fun createDifferValueRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val valueRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Value},
                ErrorMessageGenerator.generateNextObject("Шаг", "Значения"), TreeSavePosition.RootTree)

        startRule?.setNextRule(valueRule)

        val xkRule = DifferSyntaxRule({token -> token.tokenType == TokenType.Xk},
                ErrorMessageGenerator.generateNextObject("Значения", "xk"), TreeSavePosition.NodeTree)

        valueRule.setNextRule(xkRule)

        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                ErrorMessageGenerator.generateNextObject("xk", "="), TreeSavePosition.None)

        xkRule.setNextRule(equalRule)

        val numberRule = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                ErrorMessageGenerator.generateNextMultipleObject("=", listOf("Целое", "Вещественное")), TreeSavePosition.CurrentTree)

        equalRule.setNextRule(numberRule)

        val semiValueRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, ErrorMessageGenerator.generateEndSymbolObject("Значения", ";"), TreeSavePosition.None)

        numberRule.setNextRule(semiValueRule)

        val endValueRule = ComposeDifferSyntaxRule("После оператора \";\" должно следовать следующее значение(xk), либо оператор \"Уравнения\"")

        semiValueRule.setNextRule(endValueRule)

        endValueRule.setNextRule(xkRule)

        if (endRule != null) {
            endValueRule.setNextRule(endRule)
        } else {
            endValueRule.setNextRule(EofRule)
        }

        return valueRule
    }

    fun createDifferEquationRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val equationRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Equation},
                ErrorMessageGenerator.generateNextObject("Значения", "Уравнения"), TreeSavePosition.RootTree)

        startRule?.setNextRule(equationRule)

        val dxdtkRule = DifferSyntaxRule({token -> token.tokenType == TokenType.Dxdtk },
                ErrorMessageGenerator.generateNextObject("Уравнения", "dxdtk"), TreeSavePosition.NodeTree)

        equationRule.setNextRule(dxdtkRule)

        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                ErrorMessageGenerator.generateNextObject("dxdtk", "="), TreeSavePosition.None)

        dxdtkRule.setNextRule(equalRule)

        val semiValueRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, ErrorMessageGenerator.generateEndSymbolObject("Уравнения", ";"), TreeSavePosition.None)

        ExpressionSyntaxRuleFactory.createExpressionRules(equalRule, semiValueRule)

        val endValueRule = ComposeDifferSyntaxRule("После оператора \";\" должно следовать следующее уравнение(dxdtk), либо оператор \"Конец\"")

        semiValueRule.setNextRule(endValueRule)

        endValueRule.setNextRule(dxdtkRule)

        if (endRule != null) {
            endValueRule.setNextRule(endRule)
        } else {
            endValueRule.setNextRule(EofRule)
        }

        return equationRule
    }
}
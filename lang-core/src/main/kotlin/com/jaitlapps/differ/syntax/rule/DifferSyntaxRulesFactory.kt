package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.error.helper.SyntaxErrorMessageGenerator
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
                SyntaxErrorMessageGenerator.generateBeginProgram(), TreeSavePosition.None)

        val endProgramRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.EndProgram},
                {SyntaxErrorMessageGenerator.generateEndProgram()}, TreeSavePosition.None)

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
                SyntaxErrorMessageGenerator.generateNextOperator("Метод"), TreeSavePosition.RootTree)

        startRule?.setNextRule(methodRule)

        val eilerRule = DifferSyntaxRule({token -> token is MethodToken},
                SyntaxErrorMessageGenerator.generateNextMultipleOperator(listOf("Эйлера", "РунгеКутты")), TreeSavePosition.NodeTree)
        methodRule.setNextRule(eilerRule)

        val endMetdodRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon},
                SyntaxErrorMessageGenerator.generateMissingOperator(";"), TreeSavePosition.None)

        eilerRule.setNextRule(endMetdodRule)

        endRule?.let { endMetdodRule.setNextRule(endRule) }

        return methodRule
    }

    fun createDifferCoefficientRules(endRule: SyntaxRule? = null): SyntaxRule {
        val coefficientRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Coefficient},
                SyntaxErrorMessageGenerator.generateNextOperator("Коеффециенты"), TreeSavePosition.RootTree)

        val coefNodeRule = DifferSyntaxRule({token -> token.tokenType == TokenType.Coefficient},
                SyntaxErrorMessageGenerator.generateNextOperator("Коеффециент"), TreeSavePosition.NodeTree)

        coefficientRule.setNextRule(coefNodeRule)

        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                SyntaxErrorMessageGenerator.generateNextOperator("="), TreeSavePosition.None)

        coefNodeRule.setNextRule(equalRule)

        val numberRule = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                SyntaxErrorMessageGenerator.generateNextMultipleOperator(listOf("Целое", "Вещественное")), TreeSavePosition.CurrentTree)

        equalRule.setNextRule(numberRule)

        val semiCoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, SyntaxErrorMessageGenerator.generateMissingOperator(";"), TreeSavePosition.None)

        numberRule.setNextRule(semiCoefficientRule)

        val endCoefficientRule = ComposeDifferSyntaxRule({"После оператора \";\" должен следовать следующий коэффициент, либо оператор \"Интервал\"."})

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
                SyntaxErrorMessageGenerator.generateNextOperator("Интервал"), TreeSavePosition.RootTree)

        startRule?.setNextRule(intervalRule)

        val numberRuleOne = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                SyntaxErrorMessageGenerator.generateNextMultipleOperator(listOf("Целое", "Вещественное")), TreeSavePosition.NodeTree)

        intervalRule.setNextRule(numberRuleOne)

        val commaRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Comma},
                SyntaxErrorMessageGenerator.generateNextOperator(","), TreeSavePosition.None)

        numberRuleOne.setNextRule(commaRule)

        val numberRuleTwo = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                SyntaxErrorMessageGenerator.generateNextMultipleOperator(listOf("Целое", "Вещественное")), TreeSavePosition.NodeTree)

        commaRule.setNextRule(numberRuleTwo)

        val semiCoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, SyntaxErrorMessageGenerator.generateMissingOperator(";"), TreeSavePosition.None)

        numberRuleTwo.setNextRule(semiCoefficientRule)

        endRule?.let { semiCoefficientRule.setNextRule(endRule) }

        return intervalRule
    }

    fun createDifferStepRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val stepRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Step},
                SyntaxErrorMessageGenerator.generateNextOperator("Шаг"), TreeSavePosition.RootTree)

        startRule?.setNextRule(stepRule)

        val numberRule = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                SyntaxErrorMessageGenerator.generateNextMultipleOperator(listOf("Целое", "Вещественное")), TreeSavePosition.NodeTree)

        stepRule.setNextRule(numberRule)

        val semiCoefficientRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, SyntaxErrorMessageGenerator.generateMissingOperator(";"), TreeSavePosition.None)

        numberRule.setNextRule(semiCoefficientRule)

        endRule?.let { semiCoefficientRule.setNextRule(endRule) }

        return stepRule
    }

    fun createDifferValueRules(startRule: SyntaxRule? = null, endRule: SyntaxRule? = null): SyntaxRule {
        val valueRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Value},
                SyntaxErrorMessageGenerator.generateNextOperator("Значения"), TreeSavePosition.RootTree)

        startRule?.setNextRule(valueRule)

        val xkRule = DifferSyntaxRule({token -> token.tokenType == TokenType.Xk},
                SyntaxErrorMessageGenerator.generateNextOperator("xk"), TreeSavePosition.NodeTree)

        valueRule.setNextRule(xkRule)

        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                SyntaxErrorMessageGenerator.generateNextOperator("="), TreeSavePosition.None)

        xkRule.setNextRule(equalRule)

        val numberRule = DifferSyntaxRule({token -> token is NumberToken &&
                (token.tokenType == TokenType.Integer || token.tokenType == TokenType.Double)},
                SyntaxErrorMessageGenerator.generateNextMultipleOperator(listOf("Целое", "Вещественное")), TreeSavePosition.CurrentTree)

        equalRule.setNextRule(numberRule)

        val semiValueRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, SyntaxErrorMessageGenerator.generateMissingOperator(";"), TreeSavePosition.None)

        numberRule.setNextRule(semiValueRule)

        val endValueRule = ComposeDifferSyntaxRule({"После оператора \";\" должно следовать следующее значение(xk), либо оператор \"Уравнения\"."})

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
                SyntaxErrorMessageGenerator.generateNextOperator("Уравнения"), TreeSavePosition.RootTree)

        startRule?.setNextRule(equationRule)

        val dxdtkRule = DifferSyntaxRule({token -> token.tokenType == TokenType.Dxdtk },
                SyntaxErrorMessageGenerator.generateNextOperator("dxdtk"), TreeSavePosition.NodeTree)

        equationRule.setNextRule(dxdtkRule)

        val equalRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Equal},
                SyntaxErrorMessageGenerator.generateNextOperator("="), TreeSavePosition.None)

        dxdtkRule.setNextRule(equalRule)

        val semiValueRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon}, SyntaxErrorMessageGenerator.generateMissingOperator(";"), TreeSavePosition.None)

        ExpressionSyntaxRuleFactory.createExpressionRules(equalRule, semiValueRule)

        val endValueRule = ComposeDifferSyntaxRule({"После оператора \";\" должно следовать следующее уравнение(dxdtk), либо оператор \"Конец\"."})

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
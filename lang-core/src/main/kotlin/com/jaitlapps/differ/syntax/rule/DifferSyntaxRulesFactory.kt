package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.SymbolToken

object DifferSyntaxRulesFactory {
    fun createDifferFullRules() : SyntaxRule {
        val programRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Program},
                {"Программа должна начинаться с оператора \"Программа\""}, false)

        val methodRule = createDifferMethodRules(programRule)

        return programRule
    }

    fun createDifferMethodRules(startRule: SyntaxRule? = null): SyntaxRule {
        val methodRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Method},
                {"За оператором \"Программа\" должен следовать оператор \"Метод\""})

        startRule?.setNextRule(methodRule)

        val eilerRule = DifferSyntaxRule({token -> token is MethodToken},
                {"За оператором \"Метод\" должно следовать название метода"})
        methodRule.setNextRule(eilerRule)

        val endMetdodRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon},
                {"Оператор \"Метод\" должен заканчиваться символом \";\""}, false)

        eilerRule.setNextRule(endMetdodRule)

        return methodRule
    }
}
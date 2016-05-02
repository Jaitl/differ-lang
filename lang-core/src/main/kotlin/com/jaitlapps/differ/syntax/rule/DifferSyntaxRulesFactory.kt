package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.SymbolToken

object DifferSyntaxRulesFactory {
    fun createDifferRules() : SyntaxRule {
        val programRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Program},
                {"Программа должна начинаться с оператора \"Программа\""}, false)

        val methodRule = DifferSyntaxRule({token -> token is KeywordToken && token.keywordType == KeywordType.Method},
                {"За оператором \"Программа\" должен следовать оператор \"Метод\""})

        programRule.nextRule = methodRule

        val eilerRule = DifferSyntaxRule({token -> token is MethodToken},
                {"За оператором \"Метод\" должно следовать название метода"})
        methodRule.nextRule = eilerRule

        val endMetdodRule = DifferSyntaxRule({token -> token is SymbolToken && token.symbolType == SymbolType.Semicolon},
                {"Оператор \"Метод\" должен заканчиваться символом \";\""}, false)

        eilerRule.nextRule = endMetdodRule

        return programRule

    }
}
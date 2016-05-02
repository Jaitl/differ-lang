package com.jaitlapps.differ.syntax.impl

import com.jaitlapps.differ.lexical.LexicalAnalyzer
import com.jaitlapps.differ.syntax.DifferSyntaxRulesFactory
import com.jaitlapps.differ.syntax.SyntaxAnalyzer
import com.jaitlapps.differ.syntax.SyntaxRule

class DifferSyntaxAnalyzer(val lexicalAnalyzer: LexicalAnalyzer) : SyntaxAnalyzer {
    private val rootRule: SyntaxRule = DifferSyntaxRulesFactory.createDifferRules()

    fun generateSyntaxTree() {
        val currentToken = lexicalAnalyzer.nextToken();

        while (true) {

        }
    }
}
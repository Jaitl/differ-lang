package com.jaitlapps.differ.factory

import com.jaitlapps.differ.interpreter.DifferInterpreter
import com.jaitlapps.differ.lexical.impl.DifferLexicalAnalyzer
import com.jaitlapps.differ.reader.impl.StringReader
import com.jaitlapps.differ.syntax.DifferSyntaxAnalyzer
import com.jaitlapps.differ.syntax.SyntaxAnalyzer
import com.jaitlapps.differ.syntax.rule.DifferSyntaxRulesFactory
import com.jaitlapps.differ.syntax.rule.SyntaxRule

object DifferFactory {
    fun createSyntaxAnalyzer(code: String, rule: SyntaxRule): SyntaxAnalyzer {
        return DifferSyntaxAnalyzer(DifferLexicalAnalyzer(StringReader(code)), rule)
    }

    fun createDifferInterpreter(code: String): DifferInterpreter {
        return DifferInterpreter(createSyntaxAnalyzer(code, DifferSyntaxRulesFactory.createDifferFullRules()))
    }
}
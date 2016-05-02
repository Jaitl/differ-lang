package com.jaitlapps.differ.syntax

import com.jaitlapps.differ.syntax.impl.DifferSyntaxRule

object DifferSyntaxRulesFactory {
    fun createDifferRules() : SyntaxRule {
        return DifferSyntaxRule(emptyList(), {"ошибочка"})
    }
}
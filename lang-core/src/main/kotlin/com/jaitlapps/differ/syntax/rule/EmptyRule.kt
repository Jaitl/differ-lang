package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.SyntaxTree

object EmptyRule: SyntaxRule {
    override fun applyRule(token: Token, rootTree: SyntaxTree, currentTree: SyntaxTree): RuleResult {
        return FailureRuleResult("Правила закончились")
    }
}
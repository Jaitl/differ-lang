package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.TreeContext

object EmptyRule: SyntaxRule {
    override fun applyRule(token: Token, treeContext: TreeContext): RuleResult {
        return FailureRuleResult("Правила закончились")
    }

    override fun setNextRule(rule: SyntaxRule) {
        throw UnsupportedOperationException()
    }
}
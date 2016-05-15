package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.TreeContext

interface SyntaxRule {
    fun applyRule(token: Token, saveContext: TreeContext): RuleResult
    fun setNextRule(rule: SyntaxRule)
}
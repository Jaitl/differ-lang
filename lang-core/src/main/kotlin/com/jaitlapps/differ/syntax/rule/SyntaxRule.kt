package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.SyntaxTree

interface SyntaxRule {
    fun applyRule(token: Token, rootTree: SyntaxTree, currentTree: SyntaxTree): RuleResult
    fun setNextRule(rule: SyntaxRule)
}
package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.SyntaxTree
import com.jaitlapps.differ.syntax.rule.RuleResult

interface SyntaxRule {
    fun applyRule(token: Token, rootTree: SyntaxTree, currentTree: SyntaxTree): RuleResult
}
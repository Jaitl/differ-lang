package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.syntax.SyntaxTree

data class SuccessRuleResult(val rule: SyntaxRule, val tree: SyntaxTree) : RuleResult
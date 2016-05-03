package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.SyntaxTree

class DifferSyntaxRule(val comparator: (token: Token) -> Boolean,
                       val currentError: () -> String, val isSaveTree: Boolean = true) : SyntaxRule {

    private var nextRule: SyntaxRule = EmptyRule

    override fun setNextRule(rule: SyntaxRule) {
        this.nextRule = rule
    }

    // TODO: Save context
    override fun applyRule(token: Token, rootTree: SyntaxTree, currentTree: SyntaxTree): RuleResult {
        if (comparator(token)) {
            if (isSaveTree) {
                val tree = SyntaxTree(token);
                currentTree.childs.add(tree)
                return SuccessRuleResult(nextRule, tree)
            }

            return SuccessRuleResult(nextRule, currentTree)
        }

        return FailureRuleResult(currentError())
    }

}
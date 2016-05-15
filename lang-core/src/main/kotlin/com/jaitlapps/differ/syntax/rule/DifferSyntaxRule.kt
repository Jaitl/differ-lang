package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.SyntaxTree
import com.jaitlapps.differ.syntax.TreeContext

class DifferSyntaxRule(val comparator: (token: Token) -> Boolean,
                       val currentError: (token: Token) -> String, val savePosition: TreeSavePosition) : SyntaxRule {

    private var nextRule: SyntaxRule = EofRule

    override fun setNextRule(rule: SyntaxRule) {
        this.nextRule = rule
    }

    override fun applyRule(token: Token, saveContext: TreeContext): RuleResult {
        if (comparator(token)) {
            if (savePosition != TreeSavePosition.None) {
                val tree = SyntaxTree(token);

                if (savePosition == TreeSavePosition.RootTree) saveContext.rootTree.childs.add(tree)
                else if (savePosition == TreeSavePosition.NodeTree) saveContext.nodeTree.childs.add(tree)
                else if (savePosition == TreeSavePosition.CurrentTree) saveContext.currentTree.childs.add(tree)

                return SuccessRuleResult(nextRule, tree)
            }

            return SuccessRuleResult(nextRule, saveContext.currentTree)
        }

        return FailureRuleResult(currentError(token))
    }
}
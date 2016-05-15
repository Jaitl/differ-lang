package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.error.helper.SyntaxErrorMessageGenerator
import com.jaitlapps.differ.exceptions.EofRuleException
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.TreeContext

object EofRule : SyntaxRule {
    override fun applyRule(token: Token, treeContext: TreeContext): RuleResult {
        if (token.tokenType == TokenType.Eof) {
            return SuccessRuleResult(EofRule, treeContext.currentTree)
        }
        return FailureRuleResult(SyntaxErrorMessageGenerator.generateEndProgram())
    }

    override fun setNextRule(rule: SyntaxRule) {
        throw EofRuleException(SyntaxErrorMessageGenerator.generateEndProgram())
    }
}
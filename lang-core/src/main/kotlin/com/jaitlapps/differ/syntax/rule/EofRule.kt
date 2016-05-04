package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.TreeContext
import com.jaitlapps.differ.syntax.error.ErrorMessageGenerator
import com.jaitlapps.differ.syntax.exception.EofRuleException

object EofRule : SyntaxRule {
    override fun applyRule(token: Token, treeContext: TreeContext): RuleResult {
        if (token.tokenType == TokenType.Eof) {
            return SuccessRuleResult(EofRule, treeContext.currentTree)
        }
        return FailureRuleResult(ErrorMessageGenerator.generateEndProgram("Конец"))
    }

    override fun setNextRule(rule: SyntaxRule) {
        throw EofRuleException(ErrorMessageGenerator.generateEndProgram("Конец"))
    }
}
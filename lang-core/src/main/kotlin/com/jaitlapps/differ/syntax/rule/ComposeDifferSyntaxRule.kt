package com.jaitlapps.differ.syntax.rule

import com.jaitlapps.differ.model.token.Token
import com.jaitlapps.differ.syntax.TreeContext
import java.util.*

class ComposeDifferSyntaxRule(val currentError: (token:Token) -> String) : SyntaxRule {

    val rules: ArrayList<SyntaxRule> = arrayListOf()

    override fun setNextRule(rule: SyntaxRule) {
        rules.add(rule)
    }

    override fun applyRule(token: Token, saveContext: TreeContext): RuleResult {
        if (rules.size == 0) {
            throw Exception("not rules")
        }

        if (rules.size == 1) {
            return rules[0].applyRule(token, saveContext)
        }

        for (rule in rules) {
            val result = rule.applyRule(token, saveContext)
            when(result) {
                is SuccessRuleResult -> return result
            }
        }

        return FailureRuleResult(currentError(token))
    }
}
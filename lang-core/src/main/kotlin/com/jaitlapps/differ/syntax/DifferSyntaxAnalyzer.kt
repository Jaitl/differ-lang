package com.jaitlapps.differ.syntax

import com.jaitlapps.differ.exceptions.SyntaxException
import com.jaitlapps.differ.lexical.LexicalAnalyzer
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.Word
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.syntax.rule.EofRule
import com.jaitlapps.differ.syntax.rule.FailureRuleResult
import com.jaitlapps.differ.syntax.rule.SuccessRuleResult
import com.jaitlapps.differ.syntax.rule.SyntaxRule

class DifferSyntaxAnalyzer(val lexicalAnalyzer: LexicalAnalyzer, val rootRule: SyntaxRule) : SyntaxAnalyzer {
    private val rootTree: SyntaxTree = SyntaxTree(KeywordToken(Word(0, 0, "", ""), TokenType.Keyword, KeywordType.Program))

    override fun generateSyntaxTree(): SyntaxTree {
        var currentToken = lexicalAnalyzer.nextToken()
        var currentRule = rootRule
        var currentTree = rootTree
        var currentNode = rootTree

        while (currentRule !is EofRule || currentToken.tokenType != TokenType.Eof) {

            val result = currentRule.applyRule(currentToken, TreeContext(rootTree, currentNode, currentTree))

            when(result) {
                is SuccessRuleResult -> {currentRule = result.rule; currentTree = result.tree}
                is FailureRuleResult -> throw SyntaxException(result.errorMessage, currentToken.prevToken!!)
            }

            if (currentToken is KeywordToken && currentToken.keywordType.priority == 1) {
                currentNode = currentTree
            }

            currentToken = lexicalAnalyzer.nextToken();
        }

        return rootTree
    }
}
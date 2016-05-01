package com.jaitlapps.differ.lexical

import com.jaitlapps.differ.model.token.Token

interface LexicalAnalyzer {
    fun nextToken(): Token
}

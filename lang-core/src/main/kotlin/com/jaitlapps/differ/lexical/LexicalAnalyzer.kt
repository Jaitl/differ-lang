package com.jaitlapps.differ.lexical

import com.jaitlapps.differ.model.Token

interface LexicalAnalyzer {
    fun nextToken(): Token
}

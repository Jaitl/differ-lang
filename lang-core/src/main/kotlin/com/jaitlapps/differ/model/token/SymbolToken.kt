package com.jaitlapps.differ.model.token

import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.Word

class SymbolToken(word: Word, tokenType: TokenType, val symbolType: SymbolType) : Token(word, tokenType) {
}
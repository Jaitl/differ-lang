package com.jaitlapps.differ.model.token

import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.Word

class NumberToken(word: Word, tokenType: TokenType, val number: Number) : Token(word, tokenType) {
}
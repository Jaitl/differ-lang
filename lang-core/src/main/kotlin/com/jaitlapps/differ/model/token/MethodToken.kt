package com.jaitlapps.differ.model.token

import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.Word

class MethodToken(word: Word, tokenType: TokenType, val methodType: MethodType) : Token(word, tokenType) {
}
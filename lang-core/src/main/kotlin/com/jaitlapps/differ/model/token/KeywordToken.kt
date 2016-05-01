package com.jaitlapps.differ.model.token

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.Word

class KeywordToken(word: Word, tokenType: TokenType, val keywordType: KeywordType) : Token(word, tokenType) {

}
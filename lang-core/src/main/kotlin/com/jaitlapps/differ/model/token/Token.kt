package com.jaitlapps.differ.model.token

import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.Word

open class Token(val word: Word, val tokenType: TokenType) {
    var prevToken: Token? = null
}


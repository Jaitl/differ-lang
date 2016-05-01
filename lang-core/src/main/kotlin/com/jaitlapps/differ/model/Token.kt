package com.jaitlapps.differ.model

class Token(val word: Word, val tokenType: TokenType) {

    var keywordType: KeywordType? = null
    var expressionType: ExpressionType? = null

    var value: Int = 0
    var valueReal: Double = 0.toDouble()

    var prevToken: Token? = null
}


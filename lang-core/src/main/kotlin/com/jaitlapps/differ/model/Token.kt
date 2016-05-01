package com.jaitlapps.differ.model

class Token {
    var sourceString: String? = null
    var capitalizedString: String? = null

    var tokenType: TokenType? = null
    var keywordType: KeywordType? = null
    var expressionType: ExpressionType? = null

    var value: Int = 0
    var valueReal: Double = 0.toDouble()

    var prevToken: Token? = null

    var startPosition: Int = 0
    var endPosition: Int = 0
}


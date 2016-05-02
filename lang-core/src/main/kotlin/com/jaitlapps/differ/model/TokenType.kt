package com.jaitlapps.differ.model

enum class TokenType(val priority: Int) {
    Keyword(1),
    Symbol(2),
    Method(2),
    Coefficient(2),
    Xk(2),
    Integer(2),
    Double(2),
    Unknown(2),
    Eof(2)
}

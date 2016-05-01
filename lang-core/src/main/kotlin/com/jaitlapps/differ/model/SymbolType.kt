package com.jaitlapps.differ.model

enum class SymbolType(val symbolName: String) {
    Equal("="),
    Comma(","),
    Semicolon(";"),
    EndLine("\n"),
    Addition("+"),
    Subtraction("-"),
    Multiplication("*"),
    Division("/"),
    Involution("ˆ"),
    OpenBracket("("),
    CloseBracket(")");

    companion object symbols {
        val SYMBOLS = hashMapOf(Equal.symbolName to Equal,
                Comma.symbolName to Comma,
                Semicolon.symbolName to Semicolon,
                EndLine.symbolName to EndLine,
                Addition.symbolName to Addition,
                Subtraction.symbolName to Subtraction,
                Multiplication.symbolName to Multiplication,
                Division.symbolName to Division,
                Involution.symbolName to Involution,
                OpenBracket.symbolName to OpenBracket,
                CloseBracket.symbolName to CloseBracket)
    }
}

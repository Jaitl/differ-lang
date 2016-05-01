package com.jaitlapps.differ.model

enum class KeywordType(val tokenName: String) {
    Program("программа");

    companion object keywords {
        val KEYWORDS = mapOf(Program.tokenName to Program)
    }
}

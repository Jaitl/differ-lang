package com.jaitlapps.differ.model

enum class KeywordType(val keywordName: String) {
    Program("программа"),
    Method("метод");

    companion object keywords {
        val KEYWORDS = mapOf(Program.keywordName to Program,
                Method.keywordName to Method)
    }
}

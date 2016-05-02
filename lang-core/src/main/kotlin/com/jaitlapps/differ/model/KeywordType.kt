package com.jaitlapps.differ.model

enum class KeywordType(val keywordName: String) {
    Program("программа"),
    Method("метод"),
    Coefficient("коэффициенты"),
    Interval("интервал"),
    Step("шаг"),
    Value("значения");

    companion object keywords {
        val KEYWORDS = mapOf(Program.keywordName to Program,
                Method.keywordName to Method,
                Coefficient.keywordName to Coefficient,
                Interval.keywordName to Interval,
                Step.keywordName to Step,
                Value.keywordName to Value)
    }
}

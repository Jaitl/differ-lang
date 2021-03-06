package com.jaitlapps.differ.model

enum class KeywordType(val keywordName: String, val priority: Int) {
    Program("программа", 0),
    EndProgram("конец", 0),
    Method("метод", 1),
    Coefficient("коэффициенты", 1),
    Interval("интервал", 1),
    Step("шаг", 1),
    Value("значения", 1),
    Equation("уравнения", 1);

    companion object keywords {
        val KEYWORDS = mapOf(Program.keywordName to Program,
                EndProgram.keywordName to EndProgram,
                Method.keywordName to Method,
                Coefficient.keywordName to Coefficient,
                Interval.keywordName to Interval,
                Step.keywordName to Step,
                Value.keywordName to Value,
                Equation.keywordName to Equation)
    }
}

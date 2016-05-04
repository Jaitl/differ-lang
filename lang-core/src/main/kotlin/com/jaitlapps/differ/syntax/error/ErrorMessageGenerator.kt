package com.jaitlapps.differ.syntax.error

object ErrorMessageGenerator {
    /**
     * За оператором \"***\" должен следовать оператор \"***\"
     */
    fun generateNextObject(operator: String, actual: String): String {
        return "За оператором \"%s\" должен следовать оператор \"%s\"".format(operator, actual)
    }

    /**
     * "За оператором \"**\" должен следовать один из операторов: \"**\", \"**\""
     */
    fun generateNextMultipleObject(operator: String, actuals: List<String>): String {
        return "За оператором \"%s\" должен следовать один из операторов: \"%s".format(operator,
                actuals.joinToString(separator = "\", \"", postfix = "\""))
    }

    /**
     * Оператор \"***\" должен заканчиваться символом \"***\"
     */
    fun generateEndSymbolObject(operator: String, symbol: String): String {
        return "Оператор \"%s\" должен заканчиваться символом \"%s\"".format(operator, symbol)
    }

    fun generateEndProgram(operator: String): String {
        return "За оператором \"%s\" не должно быть других операторов".format(operator)
    }
}
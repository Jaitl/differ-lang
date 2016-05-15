package com.jaitlapps.differ.error.helper

import com.jaitlapps.differ.model.token.Token

object SyntaxErrorMessageGenerator {
    /**
     * За оператором \"***\" должен следовать оператор \"***\"
     */
    fun generateNextOperator(actual: String): (token:Token) -> String = {
        token -> "За оператором \"%s\" должен следовать оператор \"%s\"."
                .format(token.prevToken!!.word.word, actual) }

    /**
     * "За оператором \"**\" должен следовать один из операторов: \"**\", \"**\""
     */
    fun generateNextMultipleOperator(actuals: List<String>): (token:Token) -> String  =
            { token -> "За оператором \"%s\" должен следовать один из операторов: \"%s."
                    .format(token.prevToken!!.word.word, actuals.joinToString(separator = "\", \"", postfix = "\"")) }

    /**
     * После оператора \"***\" пропущен оператор \"***\"
     */
    fun generateMissingOperator(symbol: String): (token: Token) -> String = {
        token -> "После оператора \"%s\" пропущен оператор \"%s\".".format(token.prevToken!!.word.word, symbol)}

    fun generateBeginProgram(): (token:Token) -> String  = {"Программа должна начинаться с оператора \"Программа\"."}

    fun generateEndProgram(): String = "После оператора \"Конец\" не должно быть других операторов."
}
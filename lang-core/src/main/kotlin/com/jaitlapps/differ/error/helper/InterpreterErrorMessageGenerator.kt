package com.jaitlapps.differ.error.helper

object InterpreterErrorMessageGenerator {
    fun generateOperatorAlreadyExist(operator: String, name: String): String = "$operator с именем \"$name\" уже существует. Выберите другое имя."

    fun generateManyOpenBracket(): String = "Количество открывающих скобок больше, чем количество закрывающих."

    fun generateManyCloseBracket(): String = "Количество закрывающих скобок больше, чем количество открывающих."

    fun generateCountValues(): String = "Количество значений должно быть равно количеству уравнений."

    fun generateFunOpenBracket(): String = "После названия функции должна идти открывающая скобка."

    fun generateValueNotDefined(operator: String): String = "Значение оператора \"$operator\" не определено."
}
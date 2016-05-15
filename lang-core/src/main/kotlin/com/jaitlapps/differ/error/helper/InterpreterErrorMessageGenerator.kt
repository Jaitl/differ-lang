package com.jaitlapps.differ.error.helper

object InterpreterErrorMessageGenerator {
    fun generateOperatorAlreadyExist(operator: String, name: String): String {
        return "%s с именем \"%s\" уже существует. Выберите другое имя.".format(operator, name)
    }

    fun generateManyOpenBracket(): String {
        return "Количество открывающих скобок больше, чем количество закрывающих."
    }

    fun generateManyCloseBracket(): String {
        return "Количество закрывающих скобок больше, чем количество открывающих."
    }

    fun generateCountValues(): String {
        return "Количество значений должно быть равно количеству уравнений."
    }

    fun generateFunOpenBracket(): String {
        return "После названия функции должна идти открывающая скобка."
    }
}
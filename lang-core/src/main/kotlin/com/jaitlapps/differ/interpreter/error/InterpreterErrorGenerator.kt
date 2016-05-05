package com.jaitlapps.differ.interpreter.error

object InterpreterErrorGenerator {
    fun generateOperatorAlreadyExist(operator: String, name: String): String {
        return "%s с именем \"%s\" уже существует. Выберите другое имя.".format(operator, name)
    }
}
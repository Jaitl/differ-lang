package com.jaitlapps.differ.interpreter.error

object InterpreterErrorGenerator {
    fun generateCoefficientsAlreadyExist(name: String): String {
        return "Коэффициент с именем \"%s\" уже существует. Выберите другое имя.".format(name)
    }
}
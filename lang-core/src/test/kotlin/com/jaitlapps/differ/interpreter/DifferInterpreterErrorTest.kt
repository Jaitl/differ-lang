package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.factory.DifferFactory
import com.jaitlapps.differ.interpreter.exception.InterpreterException
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class DifferInterpreterErrorTest {
    @Test
    fun testCoeffAlreadyExists() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; a = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x22 = 43;")
        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("Коэффициент с именем \"a\" уже существует. Выберите другое имя.", e.message)
        }
    }
}
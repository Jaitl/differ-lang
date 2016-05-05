package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.factory.DifferFactory
import org.junit.Test
import kotlin.test.assertEquals

class DifferInterpreterTest {
    @Test
    fun testInterpreter() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x22 = 43;")
        interpreter.run();

        assertEquals(2, interpreter.coefficients.count())
        assertEquals(10.toDouble(), interpreter.coefficients["a"])
        assertEquals(50.toDouble(), interpreter.coefficients["b"])

        assertEquals(0.toDouble(), interpreter.startInterval)
        assertEquals(50.toDouble(), interpreter.endInterval)
    }
}
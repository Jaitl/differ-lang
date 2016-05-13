package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.factory.DifferFactory
import com.jaitlapps.differ.model.MethodType
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class DifferInterpreterTest {
    @Test
    fun testInterpreter() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x22 = 43; Уравнения dxdt11 = 2+3; dxdt22 = 5+(-6)* sin(0.3); Конец")
        interpreter.run();

        assertEquals(MethodType.Euler, interpreter.method)

        assertEquals(2, interpreter.coefficients.count())
        assertEquals(10.toDouble(), interpreter.coefficients["a"])
        assertEquals(50.toDouble(), interpreter.coefficients["b"])

        assertEquals(0.toDouble(), interpreter.startInterval)
        assertEquals(50.toDouble(), interpreter.endInterval)

        assertEquals(0.6.toDouble(), interpreter.step)

        assertEquals(10.toDouble(), interpreter.xk["x11"])
        assertEquals(43.toDouble(), interpreter.xk["x22"])

        assertEquals("2.0 + 3.0", interpreter.dxdtk["dxdt11"])
        assertEquals("5.0 + ( - 6.0 ) * Math.sin ( 0.3 )", interpreter.dxdtk["dxdt22"])
    }

    @Test
    fun testEuler() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 7; Интервал 0, 2; Шаг 0.01; Значения x1 = 0.1; x2 = 0.5; Уравнения dxdt1 = x2; dxdt2 = a - 12 * x1 - 1.5 * x2; Конец")
        val res = interpreter.run();

        Assert.assertEquals(201, res.labels.size)
        Assert.assertEquals("0,010", res.labels[1])

        Assert.assertEquals(2, res.datasets.size)

        val dxdt1 = res.datasets.first { it.label.equals("dxdt1") }
        Assert.assertEquals(201, dxdt1.data.size)
        Assert.assertEquals(0.105, dxdt1.data[1], 0.001)

        val dxdt2 = res.datasets.first { it.label.equals("dxdt2") }
        Assert.assertEquals(201, dxdt2.data.size)
        Assert.assertEquals(0.5505, dxdt2.data[1], 0.001)
    }
}
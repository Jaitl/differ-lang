package com.jaitlapps.differ.interpreter.methods

import org.junit.Assert.assertEquals
import org.junit.Test

class EulerMethodTest {
    @Test
    fun evaluateExpressionTest() {
        val euler = EulerMethod()
        val result = euler.evaluateExpression(mapOf("x1" to 4.0, "y" to 5.0, "d" to 10.0), "x1 + y")

        assertEquals(9.0, result, 0.1)
    }

    @Test
    fun calculateTest() {
        val euler = EulerMethod()

        val coeff = mapOf("a" to 7.0)
        val xk = mapOf("x1" to 0.1, "x2" to 0.5)
        val startInterval = 0.0
        val endInterval = 2.0
        val step = 0.01
        val expressions = mapOf("dxdt1" to "x2", "dxdt2" to "a - 12 * x1 - 1.5 * x2")

        val res = euler.calculate(coeff, xk, startInterval, endInterval, step, expressions)

        assertEquals(201, res.labels.size)
        assertEquals("0,010", res.labels[1])

        assertEquals(2, res.datasets.size)

        val dxdt1 = res.datasets.first { it.label.equals("dxdt1") }
        assertEquals(201, dxdt1.data.size)
        assertEquals(0.105, dxdt1.data[1], 0.001)

        val dxdt2 = res.datasets.first { it.label.equals("dxdt2") }
        assertEquals(201, dxdt2.data.size)
        assertEquals(0.5505, dxdt2.data[1], 0.001)
    }
}
package com.jaitlapps.differ.interpreter.methods

import org.junit.Assert
import org.junit.Test

class RungeKutta4MethodTest {
    @Test
    fun calculateTest() {
        val runge4 = RungeKutta4Method()

        val coeff = mapOf("a" to 7.0)
        val xk = mapOf("x1" to 0.1, "x2" to 0.5)
        val startInterval = 0.0
        val endInterval = 2.0
        val step = 0.01
        val expressions = mapOf("dxdt1" to "x2", "dxdt2" to "a - 12 * x1 - 1.5 * x2")

        val res = runge4.calculate(coeff, xk, startInterval, endInterval, step, expressions)

        Assert.assertEquals(201, res.labels.size)
        Assert.assertEquals("0,010", res.labels[1])

        Assert.assertEquals(2, res.datasets.size)

        val dxdt1 = res.datasets.first { it.label.equals("dxdt1") }
        Assert.assertEquals(201, dxdt1.data.size)
        Assert.assertEquals(0.105, dxdt1.data[1], 0.001)

        val dxdt2 = res.datasets.first { it.label.equals("dxdt2") }
        Assert.assertEquals(201, dxdt2.data.size)
        Assert.assertEquals(0.5501, dxdt2.data[1], 0.001)
    }
}
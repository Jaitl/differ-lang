package com.jaitlapps.differ.interpreter.methods

import java.util.*

class EulerMethod : AbstractMethod() {

    override fun calculate(coeff: Map<String, Double>, xk: Map<String, Double>, startInterval: Double, endInterval: Double, step: Double, expressions: Map<String, String>): MethodResult {
        val currentXk = HashMap<String, ArrayList<Double>>()
        for((x,k) in xk) {
            val array = ArrayList<Double>()
            array.add(k)
            currentXk.put(x, array)
        }

        val steps = ArrayList<Double>()
        var current = startInterval
        do {
            steps.add(current)
            current += step
        } while(current <= endInterval + step)

        var pos = 1
        for (st in steps.subList(1, steps.size)) {
            for ((diff, exp) in expressions) {
                val diffx = diff.replace("dxdt", "x")
                val mapParams = HashMap<String, Double>()
                mapParams.putAll(coeff)
                mapParams.put("h", st)
                mapParams.putAll(getCurrentXk(currentXk, pos))

                val bExp = "$diffx + h * ($exp)"
                val resultVal = evaluateExpression(mapParams, bExp)
                saveCurrentXk(currentXk, diffx, resultVal)
            }
            pos += 1
        }

        return createResult(steps, currentXk);
    }
}
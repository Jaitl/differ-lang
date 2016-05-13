package com.jaitlapps.differ.interpreter.methods

import org.mvel2.MVEL
import java.util.*

class EulerMethod : Method {

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

    fun createResult(steps: List<Double>, xk: HashMap<String, ArrayList<Double>>): MethodResult {
        val labels = steps.map { "%.3f".format(it) }

        val datasets = ArrayList<DataSet>()

        for ((diff, value) in xk) {
            val dxdt = diff.replace("x", "dxdt")
            val datas = DataSet(dxdt, value)
            datasets.add(datas)
        }

        return MethodResult(labels, datasets)
    }

    fun getCurrentXk(xk: HashMap<String, ArrayList<Double>>, pos: Int): Map<String, Double> {
        val resMap = HashMap<String, Double>()

        for((x, values) in xk) {
            resMap.put(x, values[pos-1])
        }

        return resMap
    }

    fun saveCurrentXk(xkRes: HashMap<String, ArrayList<Double>>, xk: String, value: Double) {
        val arr = xkRes[xk]!!
        arr.add(value)
    }

    fun evaluateExpression(params: Map<String, Double>, expression: String): Double {
        val expres = MVEL.compileExpression(expression)
        return MVEL.executeExpression(expres, params) as Double
    }
}
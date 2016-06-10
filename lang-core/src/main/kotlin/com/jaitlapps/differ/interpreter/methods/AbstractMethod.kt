package com.jaitlapps.differ.interpreter.methods

import org.mvel2.MVEL
import java.util.*

abstract class AbstractMethod: Method {
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
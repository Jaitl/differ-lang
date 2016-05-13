package com.jaitlapps.differ.interpreter.methods

interface Method {
    fun calculate(coeff: Map<String, Double>, xk: Map<String, Double>, startInterval: Double, endInterval: Double, step: Double, expressions: Map<String, String>): MethodResult
}
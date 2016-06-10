package com.jaitlapps.differ.model

enum class MethodType(val methodName: String) {
    Euler("эйлера"),
    RungeKutta4("рунгекутта4"),
    RungeKutta2("рунгекутта2");

    companion object methods {
        val METHODS = mapOf(Euler.methodName to Euler,
                RungeKutta4.methodName to RungeKutta4,
                RungeKutta2.methodName to RungeKutta2)
    }
}
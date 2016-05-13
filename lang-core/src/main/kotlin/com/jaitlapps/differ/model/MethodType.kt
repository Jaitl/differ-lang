package com.jaitlapps.differ.model

enum class MethodType(val methodName: String) {
    Euler("эйлера");

    companion object methods {
        val METHODS = mapOf(Euler.methodName to Euler)
    }
}
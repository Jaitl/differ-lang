package com.jaitlapps.differ.model

enum class MethodType(val methodName: String) {
    Eiler("эйлера");

    companion object methods {
        val METHODS = mapOf(Eiler.methodName to Eiler)
    }
}
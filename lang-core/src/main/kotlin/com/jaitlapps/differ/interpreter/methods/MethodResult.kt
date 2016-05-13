package com.jaitlapps.differ.interpreter.methods

data class MethodResult(val labels: List<String>, val datasets: List<DataSet>)

data class DataSet(val label: String, val data: List<Double>)
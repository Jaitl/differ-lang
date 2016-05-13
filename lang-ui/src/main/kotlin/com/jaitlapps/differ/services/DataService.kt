package com.jaitlapps.differ.services

object DataService {
    fun getBnf(): String {
        return javaClass.getClassLoader()
                .getResource("data/bnf.txt").readText(Charsets.UTF_8)
    }

    fun getProgram(): String {
        return javaClass.getClassLoader()
                .getResource("data/program.txt").readText(Charsets.UTF_8)
    }
}
package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.syntax.SyntaxAnalyzer
import com.jaitlapps.differ.syntax.SyntaxTree
import java.util.*

class DifferInterpreter(val syntax: SyntaxAnalyzer) {
    val coefficients: HashMap<String, Double> = hashMapOf()
    val xk: HashMap<String, Double> = hashMapOf()

    var startInterval: Double? = null
    var endInterval: Double? = null
    var step: Double? = null
    var method: MethodType? = null

    fun run() {
        val syntaxTree: SyntaxTree = syntax.generateSyntaxTree()


    }

    private fun extractCoefficients(tree: SyntaxTree) {
        val coefficients = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Coefficient }

        if (coefficients != null) {
            for (node in coefficients.childs) {

            }
        }
    }
}
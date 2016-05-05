package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.interpreter.error.InterpreterErrorGenerator
import com.jaitlapps.differ.interpreter.exception.InterpreterException
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.NumberToken
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
        extractMethod(syntaxTree)
        extractCoefficients(syntaxTree)
        extractInterval(syntaxTree)

    }

    private fun extractMethod(tree: SyntaxTree) {

    }

    private fun extractCoefficients(tree: SyntaxTree) {
        val coefficientsTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Coefficient }

        if (coefficientsTree != null) {
            for (node in coefficientsTree.childs) {
                val coefName = node.token.word.word
                if (coefficients.containsKey(coefName)) {
                    throw InterpreterException(InterpreterErrorGenerator.generateCoefficientsAlreadyExist(coefName))
                }
                if (node.childs.count() == 1) {
                    val coefValue = node.childs[0].token as NumberToken
                    coefficients.put(coefName, coefValue.number.toDouble())
                } else {
                    throw Exception("Coefficient value not found")
                }
            }
        } else {
            throw Exception("Coefficients node not found")
        }
    }

    private fun extractInterval(tree: SyntaxTree) {
        val intervalTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Interval }

        if (intervalTree != null) {
            if (intervalTree.childs.count() == 2) {
                val numbers = intervalTree.childs
                        .map{tree -> tree.token as NumberToken}
                        .map{token -> token.number.toDouble()}

                startInterval = numbers.min()
                endInterval = numbers.max()
            } else {
                throw Exception("Interval count error")
            }
        } else {
            throw Exception("Interval node not found")
        }
    }
}
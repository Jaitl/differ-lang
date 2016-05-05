package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.interpreter.error.InterpreterErrorGenerator
import com.jaitlapps.differ.interpreter.exception.InterpreterException
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
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
        extractStep(syntaxTree)
        extractXk(syntaxTree)
    }

    private fun extractMethod(tree: SyntaxTree) {
        val methodTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Method }

        if (methodTree != null) {
            if (methodTree.childs.count() == 1) {
                method = (methodTree.childs[0].token as MethodToken).methodType
            } else {
                throw Exception("Method node not found")
            }
        } else {
            throw Exception("Method value not found")
        }
    }

    private fun extractCoefficients(tree: SyntaxTree) {
        val coefficientsTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Coefficient }

        if (coefficientsTree != null) {
            for (node in coefficientsTree.childs) {
                val coefName = node.token.word.word
                if (coefficients.containsKey(coefName)) {
                    throw InterpreterException(InterpreterErrorGenerator.generateOperatorAlreadyExist("Коэффициент", coefName))
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

    fun extractStep(tree: SyntaxTree) {
        val stepTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Step }

        if (stepTree != null) {
            if (stepTree.childs.count() == 1) {
                step = (stepTree.childs[0].token as NumberToken).number.toDouble()
            } else {
                throw Exception("Step node not found")
            }
        } else {
            throw Exception("Step value not found")
        }
    }

    fun extractXk(tree: SyntaxTree) {
        val xkTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Value }

        if (xkTree != null) {
            for (node in xkTree.childs) {
                val xkName = node.token.word.word
                if (xk.containsKey(xkName)) {
                    throw InterpreterException(InterpreterErrorGenerator.generateOperatorAlreadyExist("xk", xkName))
                }
                if (node.childs.count() == 1) {
                    val xkValue = node.childs[0].token as NumberToken
                    xk.put(xkName, xkValue.number.toDouble())
                } else {
                    throw Exception("xk value not found")
                }
            }
        } else {
            throw Exception("xk node not found")
        }
    }
}
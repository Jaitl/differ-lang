package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.error.helper.InterpreterErrorMessageGenerator
import com.jaitlapps.differ.exceptions.InterpreterException
import com.jaitlapps.differ.interpreter.methods.EulerMethod
import com.jaitlapps.differ.interpreter.methods.MethodResult
import com.jaitlapps.differ.interpreter.methods.RungeKutta2Method
import com.jaitlapps.differ.interpreter.methods.RungeKutta4Method
import com.jaitlapps.differ.model.KeywordType
import com.jaitlapps.differ.model.MethodType
import com.jaitlapps.differ.model.SymbolType
import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.KeywordToken
import com.jaitlapps.differ.model.token.MethodToken
import com.jaitlapps.differ.model.token.NumberToken
import com.jaitlapps.differ.model.token.SymbolToken
import com.jaitlapps.differ.syntax.SyntaxAnalyzer
import com.jaitlapps.differ.syntax.SyntaxTree
import java.util.*

class DifferInterpreter(val syntax: SyntaxAnalyzer) {
    val coefficients: HashMap<String, Double> = hashMapOf()
    val xk: HashMap<String, Double> = hashMapOf()
    val dxdtk: HashMap<String, String> = hashMapOf()

    var startInterval: Double? = null
    var endInterval: Double? = null
    var step: Double? = null
    var method: MethodType? = null

    var countBracket = 0;

    fun run(): MethodResult {
        val syntaxTree: SyntaxTree = syntax.generateSyntaxTree()
        extractMethod(syntaxTree)
        extractCoefficients(syntaxTree)
        extractInterval(syntaxTree)
        extractStep(syntaxTree)
        extractXk(syntaxTree)
        extractDxdtk(syntaxTree)

        val methodCalc = when(method!!) {
            MethodType.Euler -> EulerMethod()
            MethodType.RungeKutta2 -> RungeKutta2Method()
            MethodType.RungeKutta4 -> RungeKutta4Method()
        }

        return methodCalc.calculate(coefficients, xk, startInterval!!, endInterval!!, step!!, dxdtk)
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
                    throw InterpreterException(InterpreterErrorMessageGenerator.generateOperatorAlreadyExist("Коэффициент", coefName), node.token)
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
                    throw InterpreterException(InterpreterErrorMessageGenerator.generateOperatorAlreadyExist("xk", xkName), node.token)
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

    fun extractDxdtk(tree: SyntaxTree) {
        val dxdtkTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Equation }

        if (dxdtkTree != null) {

            if (dxdtkTree.childs.count() != xk.count()) {
                val xkTree = tree.childs.findLast { tree -> val token = tree.token; token is KeywordToken && token.keywordType == KeywordType.Value }
                throw InterpreterException(InterpreterErrorMessageGenerator.generateCountValues(), xkTree!!.token)
            }

            for (node in dxdtkTree.childs) {
                val dxdtkName = node.token.word.word
                if (dxdtk.containsKey(dxdtkName)) {
                    throw InterpreterException(InterpreterErrorMessageGenerator.generateOperatorAlreadyExist("dxdtk", dxdtkName), node.token)
                }
                if (node.childs.count() == 1) {
                    val dxdtkVal = prepareFuns(parseEquation(node.childs[0]))
                    dxdtk.put(dxdtkName, dxdtkVal)

                    if (countBracket != 0) {
                        if (countBracket > 0) {
                            throw InterpreterException(InterpreterErrorMessageGenerator.generateManyOpenBracket(), node.token)
                        } else {
                            throw InterpreterException(InterpreterErrorMessageGenerator.generateManyCloseBracket(), node.token)
                        }
                    }

                } else {
                    throw Exception("xk value not found")
                }
            }
        } else {
            throw Exception("dxdtk node not found")
        }
    }

    fun parseEquation(tree: SyntaxTree): String {
        if (tree.token is SymbolToken) {
            if (tree.token.symbolType == SymbolType.OpenBracket) {
                countBracket++
            } else if (tree.token.symbolType == SymbolType.CloseBracket) {
                countBracket--;
            }
        }

        val prevToken = tree.token.prevToken!!

        if (prevToken is SymbolToken) {
            if (prevToken.symbolType == SymbolType.Cos
                    || prevToken.symbolType == SymbolType.Sin
                    || prevToken.symbolType == SymbolType.Tg) {
                if (tree.token !is SymbolToken) {
                    throw InterpreterException(InterpreterErrorMessageGenerator.generateFunOpenBracket(), tree.token)
                } else if (tree.token is SymbolToken && tree.token.symbolType != SymbolType.OpenBracket) {
                    throw InterpreterException(InterpreterErrorMessageGenerator.generateFunOpenBracket(), tree.token)
                }
            }
        }

        if (tree.token.tokenType == TokenType.Coefficient && !coefficients.containsKey(tree.token.word.word)) {
            throw InterpreterException(InterpreterErrorMessageGenerator.generateValueNotDefined(tree.token.word.word), tree.token)
        } else if (tree.token.tokenType == TokenType.Xk && !xk.containsKey(tree.token.word.word)) {
            throw InterpreterException(InterpreterErrorMessageGenerator.generateValueNotDefined(tree.token.word.word), tree.token)
        }

        var word = tree.token.word.word

        if (tree.token.tokenType == TokenType.Integer) {
            word += ".0"
        }

        if (tree.childs.isEmpty()) {
            return word
        }

        return word + " " + parseEquation(tree.childs[0])
    }

    val replaсeMap = mapOf("sin" to "Math.sin", "cos" to "Math.cos", "tg" to "Math.tan")

    fun prepareFuns(equal: String): String {
        var result = equal.toLowerCase()

        for((o,n) in replaсeMap) {
            if (result.contains(o)) {
                result = result.replace(o, n)
            }
        }

        return result
    }
}
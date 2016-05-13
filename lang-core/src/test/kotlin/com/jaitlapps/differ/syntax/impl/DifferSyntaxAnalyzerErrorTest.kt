package com.jaitlapps.differ.syntax.impl

import com.jaitlapps.differ.factory.DifferFactory
import com.jaitlapps.differ.syntax.exception.SyntaxException
import com.jaitlapps.differ.syntax.rule.DifferSyntaxRulesFactory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class DifferSyntaxAnalyzerErrorTest {
    @Test
    fun testProgramError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Метод;",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("Программа должна начинаться с оператора \"Программа\"", e.message)
        }
    }

    @Test
    fun testMethodError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Эйлер;",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("За оператором \"Программа\" должен следовать оператор \"Метод\"", e.message)
        }
    }

    @Test
    fun testEilerError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Метод Чтото;",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("За оператором \"Метод\" должен следовать один из операторов: \"Эйлера\", \"РунгеКутты\"", e.message)
        }
    }

    @Test
    fun testEilerEndError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Метод Эйлера Чтото",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("Оператор \"Метод\" должен заканчиваться символом \";\"", e.message)
        }
    }

    @Test
    fun testEilerEofError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Метод Эйлера",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
            fail()
        } catch(e: SyntaxException) {
            assertEquals("Оператор \"Метод\" должен заканчиваться символом \";\"", e.message)
        }
    }

    @Test
    fun testMultiCoeffError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Метод Эйлера; Коэффициенты a = 0.001; 123",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("После оператора \";\" должен следовать следующий коэффициент, либо оператор \"Интервал\"", e.message)
        }
    }

    @Test
    fun testMultiValueError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x22 = 43; 123",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("После оператора \";\" должно следовать следующее значение(xk), либо оператор \"Уравнения\"", e.message)
        }
    }

    @Test
    fun testMultiEquationError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x22 = 43; Уравнения dxdt1 = 2+3; dxdt2 = 5+6; 123",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("После оператора \";\" должно следовать следующее уравнение(dxdtk), либо оператор \"Конец\"", e.message)
        }
    }

    @Test
    fun testEndProgramError() {
        val syntaxAnalyzer = DifferFactory.createSyntaxAnalyzer("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x22 = 43; Уравнения dxdt1 = 2+3; dxdt2 = 5+6; Конец 123",
                DifferSyntaxRulesFactory.createDifferFullRules())

        try {
            syntaxAnalyzer.generateSyntaxTree();
        } catch(e: SyntaxException) {
            assertEquals("После оператора \"Конец\" не должно быть других операторов", e.message)
        }
    }
}
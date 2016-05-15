package com.jaitlapps.differ.interpreter

import com.jaitlapps.differ.exceptions.InterpreterException
import com.jaitlapps.differ.factory.DifferFactory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class DifferInterpreterErrorTest {
    @Test
    fun testCoeffAlreadyExists() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; a = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x22 = 43; Уравнения dxdt1 = 2+3; dxdt2 = 5+6; Конец")
        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("Коэффициент с именем \"a\" уже существует. Выберите другое имя.", e.message)
        }
    }

    @Test
    fun testXkAlreadyExists() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x11 = 43; Уравнения dxdt1 = 2+3; dxdt2 = 5+6; Конец")
        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("xk с именем \"x11\" уже существует. Выберите другое имя.", e.message)
        }
    }

    @Test
    fun testOpenBracket() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x12 = 43; Уравнения dxdt1 = (2+3; dxdt2 = 5+6; Конец")
        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("Количество открывающих скобок больше, чем количество закрывающих.", e.message)
        }
    }

    @Test
    fun testCloseBracket() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x12 = 43; Уравнения dxdt1 = 2+3); dxdt2 = 5+6; Конец")
        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("Количество закрывающих скобок больше, чем количество открывающих.", e.message)
        }
    }

    @Test
    fun testValuesCount() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; Уравнения dxdt1 = 2+3; dxdt2 = 5+6; Конец")

        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("Количество значений должно быть равно количеству уравнений.", e.message)
        }
    }

    @Test
    fun testFunOpenBracket() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x12 = 32; Уравнения dxdt11 = 2+3; dxdt12 = 5+tg 0.6; Конец")

        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("После названия функции должна идти открывающая скобка.", e.message)
        }
    }

    @Test
    fun testFunOpenBracket2() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 10; b = 50; Интервал 0, 50; Шаг 0.6; Значения x11 = 10; x12 = 32; Уравнения dxdt11 = 2+3; dxdt12 = 5+tg -0.6; Конец")

        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("После названия функции должна идти открывающая скобка.", e.message)
        }
    }

    @Test
    fun testCoeffNotDefined1() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 7; Интервал 0, 2; Шаг 0.01; Значения x1 = 0.1; x2 = 0.5; Уравнения dxdt1 = x2; dxdt2 = de - 12 * x1 - 1.5 * x2; Конец")

        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("Значение оператора \"de\" не определено.", e.message)
        }
    }

    @Test
    fun testCoeffNotDefined2() {
        val interpreter = DifferFactory.createDifferInterpreter("Программа Метод Эйлера; Коэффициенты a = 7; Интервал 0, 2; Шаг 0.01; Значения x1 = 0.1; x2 = 0.5; Уравнения dxdt1 = x2; dxdt2 = a - 12 * x10 - 1.5 * x2; Конец")

        try {
            interpreter.run();
            fail()
        } catch (e: InterpreterException) {
            assertEquals("Значение оператора \"x10\" не определено.", e.message)
        }
    }
}
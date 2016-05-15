package com.jaitlapps.differ.services

import com.jaitlapps.differ.model.Word
import org.junit.Test
import kotlin.test.assertEquals

class HighlightErrorTest {

    @Test
    fun testHighlight() {
		val code = "Программа\nМетод Эйлера;\nКоэффициенты\na = 10;\nb = 50;\nИнтервал 0, 50;\nШаг 0.6;\nЗначения\nx11 = 10;\nУравнения\ndxdt1 = 2+3;\ndxdt2 = 5+6;\nКонец"
        val result = "<p>Программа</p><p>Метод Эйлера;</p><p>Коэффициенты</p><p>a = 10;</p><p>b = 50;</p><p>Интервал 0, 50;</p><p>Шаг 0.6;</p><p><span class=\"error\">Значения</span></p><p>x11 = 10;</p><p>Уравнения</p><p>dxdt1 = 2+3;</p><p>dxdt2 = 5+6;</p><p>Конец</p>"

        val word = Word(startPosition=78, endPosition=86, word="Значения", capitalizedWord="значения")

        assertEquals(result, HighlightError.highlight(code, word))
    }
}
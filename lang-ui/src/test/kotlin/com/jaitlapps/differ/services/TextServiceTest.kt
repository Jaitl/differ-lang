package com.jaitlapps.differ.services

import org.junit.Test
import kotlin.test.assertEquals

class TextServiceTest {
    @Test
    fun testConvertText() {
        val text = "Программа\nХорошо\nРаботает"
        val html = "<p>Программа</p><p>Хорошо</p><p>Работает</p>"
        assertEquals(html, TextService.textToHtml(text))
    }

    @Test
    fun testConvertHtml() {
        val text = "Программа\nХорошо\nРаботает"
        val html = "<p>Программа</p><p>Хорошо</p><p>Работает</p>"

        assertEquals(text, TextService.htmlToText(html))
    }

    @Test
    fun testConvertHtmlSpace() {
        val text = "Программа \b \b\nХорошо\n\b \b Работает"
        val html = "<p>Программа &nbsp; &nbsp;</p><p>Хорошо</p><p>&nbsp; &nbsp; Работает</p>"

        assertEquals(text, TextService.htmlToText(html))
        assertEquals(html, TextService.textToHtml(text))
    }
}
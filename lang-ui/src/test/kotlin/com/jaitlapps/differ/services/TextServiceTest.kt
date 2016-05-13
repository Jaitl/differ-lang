package com.jaitlapps.differ.services

import org.junit.Test
import kotlin.test.assertEquals

class TextServiceTest {
    @Test
    fun testConvert() {
        val text = "Программа\nХорошо\nРаботает"
        val html = "<p>Программа</p><p>Хорошо</p><p>Работает</p>"
        assertEquals(html, TextService.textToHtml(text))
    }
}
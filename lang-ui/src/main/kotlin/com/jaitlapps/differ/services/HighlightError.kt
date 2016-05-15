package com.jaitlapps.differ.services

import com.jaitlapps.differ.model.Word

object HighlightError {
    fun highlight(code: String, word: Word): String {
        val codeStart = code.substring(0, word.startPosition)
        val codeEnd = code.substring(word.endPosition, code.length)
        val errorWord = word.word
        return TextService.textToHtml(codeStart + addErrorSpan(errorWord) + codeEnd)
    }

    private fun addErrorSpan(code: String): String = "<span class=\"error\">$code</span>"
}
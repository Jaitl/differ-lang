package com.jaitlapps.differ.services

object TextService {
    fun textToHtml(text: String): String {
        return  "<p>" + text.replace("\n","</p><p>") + "</p>"
    }
}
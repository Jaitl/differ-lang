package com.jaitlapps.differ.services

object TextService {
    fun textToHtml(text: String): String {
        return  "<p>" + text.replace("\n","</p><p>") + "</p>"
    }

    fun htmlToText(html: String): String {
        return  html.replace("</p><p>", "\n").replace("<p>","").replace("</p>", "")
    }
}
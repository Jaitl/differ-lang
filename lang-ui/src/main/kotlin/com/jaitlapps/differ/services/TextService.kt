package com.jaitlapps.differ.services

object TextService {
    fun textToHtml(text: String): String =  "<p>" + text
            .replace("\n","</p><p>")
            .replace("\b", "&nbsp;") + "</p>"

    fun htmlToText(html: String): String =  html
            .replace("</p><p>", "\n")
            .replace("<p>","")
            .replace("</p>", "")
            .replace("&nbsp;", "\b")
            .replace("<br />", "\n")
}
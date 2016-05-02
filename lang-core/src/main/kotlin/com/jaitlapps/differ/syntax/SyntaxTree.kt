package com.jaitlapps.differ.syntax

import com.jaitlapps.differ.model.TokenType
import com.jaitlapps.differ.model.token.Token
import java.util.*

class SyntaxTree(val token: Token) {
    val childs: ArrayList<SyntaxTree> = arrayListOf()
}
package com.jaitlapps.differ.exceptions

import com.jaitlapps.differ.model.token.Token

class SyntaxException(message: String, val token: Token) : Exception(message) {
}
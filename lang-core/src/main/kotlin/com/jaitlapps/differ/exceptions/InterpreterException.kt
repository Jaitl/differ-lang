package com.jaitlapps.differ.exceptions

import com.jaitlapps.differ.model.token.Token

public class InterpreterException(message: String, val token: Token) : Exception(message) {
}
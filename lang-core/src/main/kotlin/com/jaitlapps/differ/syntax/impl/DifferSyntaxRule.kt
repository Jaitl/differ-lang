package com.jaitlapps.differ.syntax.impl

import com.jaitlapps.differ.syntax.SyntaxRule
import java.util.*

class DifferSyntaxRule(val nextRules: List<SyntaxRule>, val errorHandler: () -> String) : SyntaxRule {

}
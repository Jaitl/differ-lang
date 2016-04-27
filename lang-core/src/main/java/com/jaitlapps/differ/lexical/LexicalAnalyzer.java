package com.jaitlapps.differ.lexical;

import com.jaitlapps.differ.model.Token;

public interface LexicalAnalyzer {
    Token nextToken();
}

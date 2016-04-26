package com.jaitlapps.differ;

public interface Reader {
    String readNextWord();
    int getStartPosition();

    int getEndPosition();
}

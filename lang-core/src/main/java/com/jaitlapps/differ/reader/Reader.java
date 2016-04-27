package com.jaitlapps.differ.reader;

public interface Reader {
    String readNextWord();
    int getStartPosition();

    int getEndPosition();
}

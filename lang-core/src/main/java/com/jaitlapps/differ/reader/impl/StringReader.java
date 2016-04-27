package com.jaitlapps.differ.reader.impl;

import com.google.common.collect.ImmutableSet;
import com.jaitlapps.differ.reader.Reader;

import java.util.Set;

public class StringReader implements Reader {

    private static final Set<Character> SEPARATORS = ImmutableSet.<Character>builder()
            .add('+')
            .add('-')
            .add('*')
            .add('/')
            .add('=')
            .add('[')
            .add(']')
            .add('^')
            .add(' ')
            .add('\n')
            .add('\r')
            .add(',')
            .build();

    private final String stream;
    private final char[] charStream;
    private final int lengthStream;

    private int startPosition;
    private int endPosition;

    public StringReader(String stream) {
        this.stream = stream;
        this.charStream = stream.toCharArray();
        this.lengthStream = stream.length();

        this.startPosition = 0;
        this.endPosition = 0;
    }

    @Override
    public String readNextWord() {
        while (endPosition < lengthStream && charStream[endPosition] == ' ') {
            endPosition++;
        }

        startPosition = endPosition;

        if (endPosition < lengthStream) {
            if (endPosition < lengthStream && SEPARATORS.contains(charStream[endPosition])) {
                endPosition++;
            } else {
                while (endPosition < lengthStream && charStream[endPosition] != ' ') {
                    if (SEPARATORS.contains(charStream[endPosition])) {
                        break;
                    }
                    endPosition++;
                }
            }

            return stream.substring(startPosition, endPosition);
        }

        return null;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }
}

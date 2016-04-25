package com.jaitlapps.differ.impl;

import com.google.common.collect.ImmutableSet;
import com.jaitlapps.differ.Reader;

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
    private final int lenghtSteram;

    private int startPosition;
    private int endPosition;

    public StringReader(String stream) {
        this.stream = stream;
        this.charStream = stream.toCharArray();
        this.lenghtSteram = stream.length();

        this.startPosition = 0;
        this.endPosition = 0;
    }

    @Override
    public String readOne() {
        return null;
    }
}

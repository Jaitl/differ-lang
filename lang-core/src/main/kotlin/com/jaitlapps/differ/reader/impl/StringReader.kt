package com.jaitlapps.differ.reader.impl

import com.google.common.collect.ImmutableSet
import com.jaitlapps.differ.model.Word
import com.jaitlapps.differ.reader.Reader

class StringReader(private val stream: String) : Reader {
    private val charStream: CharArray
    private val lengthStream: Int

    private var startPosition: Int = 0;
    private var endPosition: Int = 0;

    init {
        this.charStream = stream.toCharArray()
        this.lengthStream = stream.length
    }

    override fun readNextWord(): Word? {

        while (endPosition < lengthStream && charStream[endPosition] == ' ') {
            endPosition++
        }

        startPosition = endPosition

        if (endPosition < lengthStream) {
            if (endPosition < lengthStream && SEPARATORS.contains(charStream[endPosition])) {
                endPosition++
            } else {
                while (endPosition < lengthStream && charStream[endPosition] != ' ') {
                    if (SEPARATORS.contains(charStream[endPosition])) {
                        break
                    }
                    endPosition++
                }
            }

            return Word(startPosition, endPosition, stream.substring(startPosition, endPosition))
        }

        return null
    }

    companion object {
        private val SEPARATORS = ImmutableSet.builder<Char>().add('+').add('-').add('*').add('/').add('=').add('[').add(']').add('^').add(' ').add('\n').add('\r').add(',').build()
    }
}

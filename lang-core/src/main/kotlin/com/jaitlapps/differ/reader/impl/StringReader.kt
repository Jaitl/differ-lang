package com.jaitlapps.differ.reader.impl

import com.jaitlapps.differ.model.SymbolType
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
            if (endPosition < lengthStream && SymbolType.SYMBOLS.containsKey(charStream[endPosition].toString())) {
                endPosition++
            } else {
                while (endPosition < lengthStream && charStream[endPosition] != ' ') {
                    if (SymbolType.SYMBOLS.containsKey(charStream[endPosition].toString())) {
                        break
                    }
                    endPosition++
                }
            }

            val word = stream.substring(startPosition, endPosition)

            return Word(startPosition, endPosition, word, word.toLowerCase())
        }

        return null
    }
}

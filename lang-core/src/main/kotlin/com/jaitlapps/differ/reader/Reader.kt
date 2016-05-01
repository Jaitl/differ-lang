package com.jaitlapps.differ.reader

import com.jaitlapps.differ.model.Word

interface Reader {
    fun readNextWord(): Word?
}

package com.jaitlapps.differ.reader.impl

import org.junit.Test
import kotlin.test.*

class StringReaderTest {
    @Test
    fun testOneWord() {
        val wordReader = StringReader("Третье")

        var word = wordReader.readNextWord()

        assertEquals("Третье", word?.word)
        assertEquals(0, word?.startPosition)
        assertEquals(6, word?.endPosition)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testOneWordSpace() {
        val wordReader = StringReader("   Третье      ")

        var word = wordReader.readNextWord()

        assertEquals("Третье", word?.word)
        assertEquals(3, word?.startPosition)
        assertEquals(9, word?.endPosition)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testTwoWordPlus() {
        val wordReader = StringReader("ere+err")

        var word = wordReader.readNextWord()

        assertEquals("ere", word?.word)
        assertEquals(0, word?.startPosition)
        assertEquals(3, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("+", word?.word)
        assertEquals(3, word?.startPosition)
        assertEquals(4, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("err", word?.word)
        assertEquals(4, word?.startPosition)
        assertEquals(7, word?.endPosition)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    //@Test
    fun testTwoWordExpression() {
        val wordReader = StringReader("qwe=123--4")

        var word = wordReader.readNextWord()

        assertEquals("qwe", word?.word)
        assertEquals(0, word?.startPosition)
        assertEquals(3, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("=", word?.word)
        assertEquals(3, word?.startPosition)
        assertEquals(4, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("123", word?.word)
        assertEquals(4, word?.startPosition)
        assertEquals(7, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)
        assertEquals(7, word?.startPosition)
        assertEquals(8, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("-4", word?.word)
        assertEquals(8, word?.startPosition)
        assertEquals(10, word?.endPosition)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testTwoWordExpression1() {
        val wordReader = StringReader("qwe  =123-  4")

        var word = wordReader.readNextWord()

        assertEquals("qwe", word?.word)
        assertEquals(0, word?.startPosition)
        assertEquals(3, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("=", word?.word)
        assertEquals(5, word?.startPosition)
        assertEquals(6, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("123", word?.word)
        assertEquals(6, word?.startPosition)
        assertEquals(9, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)
        assertEquals(9, word?.startPosition)
        assertEquals(10, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("4", word?.word)
        assertEquals(12, word?.startPosition)
        assertEquals(13, word?.endPosition)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testThreeWord() {
        val wordReader = StringReader("Третье 123 12345")

        var word = wordReader.readNextWord()

        assertEquals("Третье", word?.word)
        assertEquals(0, word?.startPosition)
        assertEquals(6, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("123", word?.word)
        assertEquals(7, word?.startPosition)
        assertEquals(10, word?.endPosition)

        word = wordReader.readNextWord()

        assertEquals("12345", word?.word)
        assertEquals(11, word?.startPosition)
        assertEquals(16, word?.endPosition)

        word = wordReader.readNextWord()
        assertNull(word)
    }


    @Test
    fun testMinus1() {
        val wordReader = StringReader("22+-4")

        var word = wordReader.readNextWord()

        assertEquals("22", word?.word)

        word = wordReader.readNextWord()

        assertEquals("+", word?.word)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)

        word = wordReader.readNextWord()

        assertEquals("4", word?.word)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testMinus2() {
        val wordReader = StringReader("22- 4")

        var word = wordReader.readNextWord()

        assertEquals("22", word?.word)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)

        word = wordReader.readNextWord()

        assertEquals("4", word?.word)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testMinus3() {
        val wordReader = StringReader("22 - 4")

        var word = wordReader.readNextWord()

        assertEquals("22", word?.word)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)

        word = wordReader.readNextWord()

        assertEquals("4", word?.word)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testMinus4() {
        val wordReader = StringReader("22 -4")

        var word = wordReader.readNextWord()

        assertEquals("22", word?.word)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)

        word = wordReader.readNextWord()

        assertEquals("4", word?.word)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testMinus5() {
        val wordReader = StringReader("22-4")

        var word = wordReader.readNextWord()

        assertEquals("22", word?.word)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)

        word = wordReader.readNextWord()

        assertEquals("4", word?.word)

        word = wordReader.readNextWord()
        assertNull(word)
    }

    @Test
    fun testMinus6() {
        val wordReader = StringReader("-")

        var word = wordReader.readNextWord()

        assertEquals("-", word?.word)

        word = wordReader.readNextWord()
        assertNull(word)
    }


    @Test
    fun testMinus7() {
        val wordReader = StringReader("45 * 2 - 3")

        var word = wordReader.readNextWord()

        assertEquals("45", word?.word)

        word = wordReader.readNextWord()

        assertEquals("*", word?.word)

        word = wordReader.readNextWord()

        assertEquals("2", word?.word)

        word = wordReader.readNextWord()

        assertEquals("-", word?.word)

        word = wordReader.readNextWord()

        assertEquals("3", word?.word)

        word = wordReader.readNextWord()
        assertNull(word)
    }
}
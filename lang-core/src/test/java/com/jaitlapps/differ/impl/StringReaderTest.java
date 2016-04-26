package com.jaitlapps.differ.impl;

import com.jaitlapps.differ.Reader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringReaderTest {
    @Test
    public void testOneWord()
    {
        Reader wordReader = new StringReader("Третье");

        String word = wordReader.readNextWord();

        assertEquals("Третье", word);
        assertEquals(0, wordReader.getStartPosition());
        assertEquals(6, wordReader.getEndPosition());

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testOneWordSpace()
    {
        Reader wordReader = new StringReader("   Третье      ");

        String word = wordReader.readNextWord();

        assertEquals("Третье", word);
        assertEquals(3, wordReader.getStartPosition());
        assertEquals(9, wordReader.getEndPosition());

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testTwoWordPlus()
    {
        Reader wordReader = new StringReader("ere+err");

        String word = wordReader.readNextWord();

        assertEquals("ere", word);
        assertEquals(0, wordReader.getStartPosition());
        assertEquals(3, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("+", word);
        assertEquals(3, wordReader.getStartPosition());
        assertEquals(4, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("err", word);
        assertEquals(4, wordReader.getStartPosition());
        assertEquals(7, wordReader.getEndPosition());

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    //@Test
    public void testTwoWordExpression()
    {
        Reader wordReader = new StringReader("qwe=123--4");

        String word = wordReader.readNextWord();

        assertEquals("qwe", word);
        assertEquals(0, wordReader.getStartPosition());
        assertEquals(3, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("=", word);
        assertEquals(3, wordReader.getStartPosition());
        assertEquals(4, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("123", word);
        assertEquals(4, wordReader.getStartPosition());
        assertEquals(7, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("-", word);
        assertEquals(7, wordReader.getStartPosition());
        assertEquals(8, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("-4", word);
        assertEquals(8, wordReader.getStartPosition());
        assertEquals(10, wordReader.getEndPosition());

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testTwoWordExpression1()
    {
        Reader wordReader = new StringReader("qwe  =123-  4");

        String word = wordReader.readNextWord();

        assertEquals("qwe", word);
        assertEquals(0, wordReader.getStartPosition());
        assertEquals(3, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("=", word);
        assertEquals(5, wordReader.getStartPosition());
        assertEquals(6, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("123", word);
        assertEquals(6, wordReader.getStartPosition());
        assertEquals(9, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("-", word);
        assertEquals(9, wordReader.getStartPosition());
        assertEquals(10, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("4", word);
        assertEquals(12, wordReader.getStartPosition());
        assertEquals(13, wordReader.getEndPosition());

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testThreeWord()
    {
        Reader wordReader = new StringReader("Третье 123 12345");

        String word = wordReader.readNextWord();

        assertEquals("Третье", word);
        assertEquals(0, wordReader.getStartPosition());
        assertEquals(6, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("123", word);
        assertEquals(7, wordReader.getStartPosition());
        assertEquals(10, wordReader.getEndPosition());

        word = wordReader.readNextWord();

        assertEquals("12345", word);
        assertEquals(11, wordReader.getStartPosition());
        assertEquals(16, wordReader.getEndPosition());

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }



    @Test
    public void testMinus1()
    {
        Reader wordReader = new StringReader("22+-4");

        String word = wordReader.readNextWord();

        assertEquals("22", word);

        word = wordReader.readNextWord();

        assertEquals("+", word);

        word = wordReader.readNextWord();

        assertEquals("-", word);

        word = wordReader.readNextWord();

        assertEquals("4", word);

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testMinus2()
    {
        Reader wordReader = new StringReader("22- 4");

        String word = wordReader.readNextWord();

        assertEquals("22", word);

        word = wordReader.readNextWord();

        assertEquals("-", word);

        word = wordReader.readNextWord();

        assertEquals("4", word);

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testMinus3()
    {
        Reader wordReader = new StringReader("22 - 4");

        String word = wordReader.readNextWord();

        assertEquals("22", word);

        word = wordReader.readNextWord();

        assertEquals("-", word);

        word = wordReader.readNextWord();

        assertEquals("4", word);

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testMinus4()
    {
        Reader wordReader = new StringReader("22 -4");

        String word = wordReader.readNextWord();

        assertEquals("22", word);

        word = wordReader.readNextWord();

        assertEquals("-", word);

        word = wordReader.readNextWord();

        assertEquals("4", word);

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testMinus5()
    {
        Reader wordReader = new StringReader("22-4");

        String word = wordReader.readNextWord();

        assertEquals("22", word);

        word = wordReader.readNextWord();

        assertEquals("-", word);

        word = wordReader.readNextWord();

        assertEquals("4", word);

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }

    @Test
    public void testMinus6()
    {
        Reader wordReader = new StringReader("-");

        String word = wordReader.readNextWord();

        assertEquals("-", word);

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }


    @Test
    public void testMinus7()
    {
        Reader wordReader = new StringReader("45 * 2 - 3");

        String word = wordReader.readNextWord();

        assertEquals("45", word);

        word = wordReader.readNextWord();

        assertEquals("*", word);

        word = wordReader.readNextWord();

        assertEquals("2", word);

        word = wordReader.readNextWord();

        assertEquals("-", word);

        word = wordReader.readNextWord();

        assertEquals("3", word);

        word = wordReader.readNextWord();
        assertEquals(null, word);
    }
}
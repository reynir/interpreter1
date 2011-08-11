package interpreter;

import interpreter.values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.math.BigInteger;
import java.math.BigDecimal;

import interpreter.values.*;

public class Reader {
    private boolean eof;
    private int index;
    private int mark_index;
    private int character;
    private int mark_character;
    private int line;
    private int mark_line;
    private char previous;
    private char mark_previous;
    private boolean usePrevious;
    private boolean mark_usePrevious;
    private boolean isMarkSet;
    private java.io.Reader reader;

    private static final char EOList = ')';
    private static final char BOList = '(';


    public Reader(java.io.Reader reader) {
        this.reader = reader.markSupported() ?
            reader : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.line = 1;
        this.isMarkSet = false;
    }

    public Reader(InputStream inputStream) {
        this(new InputStreamReader(inputStream));
    }

    public Reader(String s) {
        this(new StringReader(s));
    }

    public void back() {
        if (usePrevious || index <= 0) {
            throw new UnsupportedOperationException("back() can only go 1 char back");
        }
        index -= 1;
        character -= 1;
        if (previous == '\n')
            line -= 1;
        usePrevious = true;
        eof = false;
    }

    public void mark() throws IOException {
        if (isMarkSet) {
            throw new RuntimeException("mark() was already set");
        }

        mark_index = index;
        mark_character = character;
        mark_line = line;
        mark_previous = previous;
        mark_usePrevious = usePrevious;
        isMarkSet = true;
        reader.mark(100); // what an arbitrary number
    }

    public void reset() throws IOException {
        if (!isMarkSet) {
            return; // Noo!
        }

        index = mark_index;
        character = mark_character;
        line = mark_line;
        previous = mark_previous;
        usePrevious = mark_usePrevious;
        isMarkSet = false;
        reader.reset();
    }

    public char next() throws IOException {
        int c;
        if (usePrevious) {
            usePrevious = false;
            c = previous;
        } else {
            c = reader.read();

            if (c == -1) {
                eof = true;
                c = 0;
                throw new EOFException(); // Maybe a little harsh
            }
        }
        index += 1;
        if (c == '\n') {
            line += 1;
            character += 1;
        } else {
            character += 1;
        }
        previous = (char) c;
        return (char) c;
    }

    private char skipSpace() throws IOException {
        int c;
        do {
            c = next();
        } while (Character.isWhitespace(c));

        back();
        if (c == -1)
            throw new IOException("Unexpected end of file");
        return (char) c;
    }

    private boolean isParen(char c) {
        switch(c) {
            case EOList: case BOList:
                return true;
            default:
                return false;
        }
    }

    /** @return A value of type Pair, SchemeString, SchemeSymbol,
     * SchemeBoolean, SchemeNum */
    public Value read() {
        return null;
    }

    public Pair nextPair() {
        return null;
    }
}

/* vim: set et sts=4 sw=4:
 * Loosely based on
 * https://github.com/douglascrockford/JSON-java/blob/master/JSONTokener.java
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class Tokenizer {
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
    private Reader reader;

    private static final char EOList = ')';
    private static final char BOList = '(';

    public Tokenizer(Reader reader) {
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

    public Tokenizer(InputStream inputStream) {
        this(new InputStreamReader(inputStream));
    }

    public Tokenizer(String s) {
        this(new StringReader(s));
    }

    public void back() {
        if (usePrevious || index <= 0) {
            // Noo!
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
            // Noo!
        }

        mark_index = index;
        mark_character = character;
        mark_line = line;
        mark_previous = previous;
        mark_usePrevious = usePrevious;
        isMarkSet = true;
        reader.mark(50);
    }

    public void reset() throws IOException {
        if (!isMarkSet) {
            // Noo!
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
                throw new EOFException();
            }
        }
        index += 1;
        if (c == '\n') {
            line += 1;
            character += 1;
        } else {
            character += 1;
        }
        previous = (char) c; // Type coercion?
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

    private boolean isReservedChar(char c) {
        switch(c) {
            case EOList: case BOList:
                return true;
            default:
                return false;
        }
    }

    private String nextToken() throws IOException, EmptyTokenException {
        StringBuilder tok = new StringBuilder();
        skipSpace();
        char c = next();

        while (!isReservedChar(c) && !Character.isWhitespace(c)) {
            tok.append(c);
            c = next();
        }
        back();

        String res = tok.toString();
        if ("".equals(res))
            throw new EmptyTokenException();
        return res;
    }

    private SchemeSymbol nextSymbol() throws IOException, EmptyTokenException {
        return new SchemeSymbol(nextToken());
    }

    private SchemeIdentifier nextIdentifier() throws IOException, EmptyTokenException {
        return new SchemeIdentifier(nextToken());
    }

    public Value nextValue() throws IOException {
        char c;

        switch (c=skipSpace()) {
            case '"': return nextString();
            case BOList: 
                      next();
                      return firstCons();
            case EOList: throw new IOException("Unexpected end of list"); // FIXME
        }
        if (Character.isDigit(c) || c == '-')
            return nextNum();
        
        String res;
        try {
            res = nextToken();
        } catch (EmptyTokenException e) {
            throw new IOException("FIXME");
        }

        if ("quote".equals(res)) {
            throw new IOException("Illegal quote something"); //FIXME
        } else {
            return new SchemeIdentifier(res);
        }
    }

    private Value firstCons() throws IOException {
        // Is it special form?
        mark();
        String t;
        try {
            t = nextToken();
            if ("quote".equals(t))
                return nextQuote();
        } catch (EmptyTokenException e) {}
        reset();
        // not special form

        Value car = nextValue();
        if (skipSpace() == EOList) {
            next();
            return new Cons(car, Cons.NULL);
        } else {
            return new Cons(car, nextCons());
        }
    }

    private Value nextCons() throws IOException {
        Value car = nextValue();
        if (skipSpace() == EOList) {
            next();
            return new Cons(car, Cons.NULL);
        } else {
            return new Cons(car, nextCons());
        }
    }

    public Quote nextQuote() throws IOException {
        skipSpace();
        Value q = nextQuotation();
        if (skipSpace() != EOList)
            throw new IOException("Expected end of list"); // FIXME
        next();
        return new Quote(q);
    }

    public Lambda nextLambda() throws IOException {
        if (skipSpace() == BOList) {

        } else {
            SchemeIdentifier formals;
            try {
                formals = nextIdentifier();
            }catch (EmptyTokenException e) {
                throw new IOException("Illegal lambda expression");
            }
        }
        return null; //FIXME
    }

    public Value nextQuotation() throws IOException {
        char c = skipSpace();
        switch (c) {
            case BOList:
                next();
                return nextQuotedList();
            case '"':
                return nextString();
        }

        if (Character.isDigit(c) || c == '-')
            return nextNum();

        try {
            return nextSymbol();
        } catch (EmptyTokenException e) {
            throw new IOException("Empty token!"); //FIXME
        }
    }

    public Cons nextQuotedList() throws IOException {
        Value car = nextQuotation();
        if (skipSpace() == EOList) {
            next();
            return new Cons(car, Cons.NULL);
        } else {
            return new Cons(car, nextQuotedList());
        }
    }

    //FIXME
    private Cons nextQuotedCons() {
        return new Cons(new SchemeSymbol("Not"), new SchemeSymbol("Implemented"));
    }

    public SchemeString nextString() throws IOException {
        int c = next();
        boolean isEscaped = false;
        StringBuilder res = new StringBuilder();

        /* Not EOF && not EOS */
        while ((c=next()) != -1) {
            if (!(isEscaped || c != '"'))
                break;
            if (isEscaped) {
                switch(c) {
                    case '\\': res.append('\\'); break;
                    case '"': res.append('"'); break;
                    case 't': res.append('\t'); break;
                    case 's': res.append(' '); break;
                    case 'n': res.append('\n'); break;
                    case 'r': res.append('\r'); break;
                    case 'b': res.append('\b'); break;
                    case '\'': res.append('\''); break;
                    default: /* TODO dunno */ break;
                }

                isEscaped = false;
            } else {
                if (c == '\\') {
                    isEscaped = true;
                    continue;
                }
                res.append((char) c);
            }

        }

        if (c == -1) {
            throw new EOFException("End of file reached while parsing "+
                    "string at line "+line);
        }
        if (c != '"') {
            // Either EOF or some other error
        }

        return new SchemeString(res.toString());
    }

    /* TODO: more numbers! */
    public SchemeNum nextNum() throws IOException {
        char c;
        int res=0;

        while (Character.isDigit(c=next())) {
            res *= 10;
            res += c-'0';
        }
        back();

        return new SchemeNum(res);
    }

    private class EmptyTokenException extends Exception {

    }
}

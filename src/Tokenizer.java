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
    private int character;
    private boolean eof;
    private int index;
    private int line;
    private char previous;
    private Reader reader;
    private boolean usePrevious;
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

    private String nextToken() throws IOException {
        StringBuilder res = new StringBuilder();
        char c = next();

        while (!isReservedChar(c) && !Character.isWhitespace(c)) {
            res.append(c);
            c = next();
        }
        back();

        return res.toString();
    }

    public Value nextValue() throws IOException {
        char c;

        switch (c=skipSpace()) {
            case '"': return nextString();
            case BOList: 
                      next();
                      return nextCons();
            case EOList: throw new IOException("Unexpected end of list"); // FIXME
        }
        if (Character.isDigit(c) || c == '-')
            return nextNum();
        
        String res = nextToken();
        if ("quote".equals(res)) {
            return new Quote();
        } else {
            return nextIdentifier();
        }
    }

    public Cons nextCons() throws IOException {
        Value car = nextValue();
        if (skipSpace() == EOList) {
            next();
            return new Cons(car, Cons.NULL);
        } else {
            return new Cons(car, nextCons());
        }
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
}

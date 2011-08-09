package interpreter.values;


public final class Null implements Value {
    public static Null NULL = new Null();

    private Null() {

    }

    public String toString() {
        return "null";
    }
}

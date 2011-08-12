package interpreter.values;


public class SchemeString implements Value {
    private final String s;

    public SchemeString(String s) {
        this.s = s;
    }

    public String toString() {
        return "SchemeString["+this.s+"]";
    }
}

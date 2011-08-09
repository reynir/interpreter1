package interpreter.values;

public class SchemeSymbol implements Value {
    private final String id;

    public SchemeSymbol(String id) {
        this.id = id;
    }

    public String toString() {
        return "SchemeSymbol["+id+"]";
    }
}

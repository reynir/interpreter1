package interpreter.values;



public class If implements Value {
    private final Value test;
    private final Value consequent;
    private final Value alternative;

    public If(Value t, Value c, Value a) {
        this.test = t;
        this.consequent = c;
        this.alternative = a;
    }

    public Value getTest() {
        return test;
    }
    public Value getConsequent() {
        return consequent;
    }
    public Value getAlternative() {
        return alternative;
    }

    public String toString() {
        return String.format("If[%s, %s, %s]", test, consequent, alternative);
    }
}

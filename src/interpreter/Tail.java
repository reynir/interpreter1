package interpreter;

import interpreter.values.Value;

public class Tail implements Value {
    private final Value tail;

    public Tail(Value v) {
        tail = v;
    }

    public Value getBody() {
        return tail;
    }
}

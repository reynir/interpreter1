package interpreter;

import interpreter.values.Value;

public class Time implements Value {
    private final Value v;

    public Time(Value v) {
        this.v = v;
    }

    public Value getValue() {
        return v;
    }
}

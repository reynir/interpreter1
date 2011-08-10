package interpreter.values;

public class SetBang implements Value {
    private final SchemeIdentifier id;
    private final Value body;

    public SetBang(SchemeIdentifier id, Value body) {
        this.id = id;
        this.body = body;
    }

    public SchemeIdentifier getIdentifier() {
        return id;
    }
    public Value getBody() {
        return body;
    }
}

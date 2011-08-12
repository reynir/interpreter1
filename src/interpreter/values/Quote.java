package interpreter.values;


public class Quote implements Value {
    private final Value quoted;

    public Quote(Value toQuote) {
        this.quoted = toQuote;
    }

    public String toString() {
        return "(quote "+quoted+")";
    }

    public Value getQuotationText() {
        return quoted;
    }
}

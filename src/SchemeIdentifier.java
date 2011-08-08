

public class SchemeIdentifier implements Value {
    private final String id;

    public SchemeIdentifier(String id) {
        this.id = id;
    }

    public String toString() {
        return "SchemeIdentifier["+id+"]";
    }
}

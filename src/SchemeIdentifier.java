

public class SchemeIdentifier implements Value {
    private final String id;

    public SchemeIdentifier(String id) {
        this.id = id;
    }

    public String toString() {
        return "SchemeIdentifier["+id+"]";
    }

    public boolean equals(Object other) {
        if (!(other instanceof SchemeIdentifier))
            return false;
        SchemeIdentifier o = (SchemeIdentifier) other;
        return o.id.equals(id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}

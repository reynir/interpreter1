

public class SchemeString implements Value {
    private String s;

    public SchemeString(String s) {
        this.s = s;
    }

    public String toString() {
        return "SchemeString["+this.s+"]";
    }
}

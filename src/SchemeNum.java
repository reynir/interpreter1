

public class SchemeNum implements Value {
    private int v;

    public SchemeNum(int v) {
        this.v = v;
    }

    public String toString() {
        return "SchemeNum["+v+"]";
    }

    public int getValue() {
        return v;
    }

    public boolean equals(Object o) {
        return (o instanceof SchemeNum) &&
            ((SchemeNum) o).v == v;
    }
}

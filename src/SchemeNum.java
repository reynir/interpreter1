

public class SchemeNum implements Value {
    private int v;

    public SchemeNum(int v) {
        this.v = v;
    }

    public String toString() {
        return "SchemeNum["+v+"]";
    }
}

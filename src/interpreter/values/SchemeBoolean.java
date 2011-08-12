package interpreter.values;


public class SchemeBoolean implements Value {
    public static final SchemeBoolean TRUE = new SchemeBoolean(true);
    public static final SchemeBoolean FALSE = new SchemeBoolean(false);
    private final boolean v;

    private SchemeBoolean(boolean v) {
        this.v = v;
    }

    public String toString() {
        return v?"#t":"#f";
    }

    public static boolean isTrue(Value v) {
        return !(FALSE.equals(v));
    }

	public static SchemeBoolean make(boolean v) {
		return v ? TRUE : FALSE;
	}
}

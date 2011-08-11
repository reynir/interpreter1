package interpreter.values;
import java.math.*;

public class SchemeNum implements Value {
    private BigInteger integer;
    private BigDecimal real;

    public SchemeNum(int v) {
        integer = BigInteger.valueOf(v);
    }

    public SchemeNum(BigInteger v) {
        integer = v;
    }

    public SchemeNum(BigDecimal v) {
        real = v;
    }

    public String toString() {
        return "SchemeNum["+(real!=null?real:integer)+"]";
    }

    public boolean equals(Object other) {
        if (!(other instanceof SchemeNum))
            return false;
        SchemeNum o = (SchemeNum) other;

        if (real == null)
            return o.real != null && real.equals(o.real);
        return integer.equals(o.integer);
    }

    public SchemeNum add(SchemeNum o) {
        if (real != null) {
            if (o.real != null)
                return new SchemeNum(real.add(o.real));
            else
                return new SchemeNum(real.add(new BigDecimal(o.integer)));
        } else {
            if (o.real != null)
                return new SchemeNum(o.real.add(new BigDecimal(integer)));
            else
                return new SchemeNum(o.integer.add(integer));
        }
    }

    public SchemeNum subtract(SchemeNum o) {
        if (real != null) {
            if (o.real != null)
                return new SchemeNum(real.subtract(o.real));
            else
                return new SchemeNum(real.subtract(new BigDecimal(o.integer)));
        } else {
            if (o.real != null)
                return new SchemeNum((new BigDecimal(integer)).subtract(o.real));
            else
                return new SchemeNum(integer.subtract(o.integer));
        }
    }

    public boolean greaterThan(SchemeNum o) {
        if (real != null) {
            if (o.real != null)
                return real.compareTo(o.real) > 0;
            else
                return real.compareTo(new BigDecimal(o.integer)) > 0; 
        } else {
            if (o.real != null)
                return o.real.compareTo(new BigDecimal(integer)) < 0;
            else	
                return o.integer.compareTo(integer) < 0;
        }
    }
}

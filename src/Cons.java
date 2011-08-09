import java.util.List;
import java.util.ArrayList;

public class Cons implements Value {
    private Value car;
    private Value cdr;
    public static final Value NULL = Null.NULL;

    public Cons(Value car, Value cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public String toString() {
        return String.format("(cons %s %s)", this.car, this.cdr);
    }

    /* Circular lists make this loop forever */
    public boolean isProperList() {
        if (this.cdr == NULL)
            return true;
        if (this.cdr instanceof Cons)
            return ((Cons) this.cdr).isProperList();
        return false;
    }

    public boolean isProperListOfLength(int len) {
        if (len == 1)
            return this.cdr == NULL;
        if (this.cdr instanceof Cons)
            return ((Cons) this.cdr).isProperListOfLength(len-1);
        return false;
    }

    public List<Value> toList() {
        Cons c;
        Value v = this;
        List<Value> res = new ArrayList<Value>();
        do {
            c = (Cons) v;
            res.add(c.car());
            v = c.cdr();
        } while (v != NULL);

        return res;
    }

    public Value car() {
        return this.car;
    }

    public Value cdr() {
        return this.cdr;
    }

}

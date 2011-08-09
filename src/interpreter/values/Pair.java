package interpreter.values;
import java.util.List;
import java.util.ArrayList;

public class Pair implements Value {
    private Value car;
    private Value cdr;
    private List<Value> delay;
    public static final Value NULL = Null.NULL;

    public Pair() {

    }

    public Pair(Value car, Value cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public Pair(List<Value> list) {
        if (list.size() == 0)
            throw new IllegalArgumentException("Too short list!");
        this.car = list.remove(0);
        if (delay.size() == 0) {
            cdr = NULL;
        } else {
            delay = list;
        }
    }

    public String toString() {
        return String.format("(cons %s %s)", this.car, this.cdr);
    }

    /* Circular lists make this loop forever */
    public boolean isProperList() {
        if (this.cdr == NULL)
            return true;
        if (this.cdr instanceof Pair)
            return ((Pair) this.cdr).isProperList();
        return false;
    }

    public boolean isProperListOfLength(int len) {
        if (len == 1)
            return this.cdr == NULL;
        if (this.cdr instanceof Pair)
            return ((Pair) this.cdr).isProperListOfLength(len-1);
        return false;
    }

    public List<Value> toList() {
        Pair c;
        Value v = this;
        List<Value> res = new ArrayList<Value>();
        do {
            c = (Pair) v;
            res.add(c.car());
            v = c.cdr();
        } while (v != NULL);

        return res;
    }

    public static Value makeConsList(List<Value> vals) {
        if (vals.size() == 0)
            return NULL;
        return new Pair(vals);
    }

    public Value car() {
        return this.car;
    }

    public Value cdr() {
        if (delay != null) {
            return new Pair(delay);
        }
        return this.cdr;
    }
}

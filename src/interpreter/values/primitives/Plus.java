package interpreter.values.primitives;

import interpreter.values.Primitive;
import interpreter.values.SchemeNum;
import interpreter.values.Value;

import java.util.List;

public class Plus implements Primitive {
    public Value apply(List<Value> args) throws IllegalArgumentException {
        SchemeNum res = new SchemeNum(0);
        for (Value v : args) {
            if (!(v instanceof SchemeNum))
                throw new IllegalArgumentException("Type exception: "+
                        v+" is not a number!");
            SchemeNum n = (SchemeNum) v;
            res = res.add(n);
        }
        return res;
    }

    public String toString() {
        return "PrimitivePlus";
    }
}

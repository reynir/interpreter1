package interpreter.values.primitives;

import interpreter.values.Primitive;
import interpreter.values.SchemeNum;
import interpreter.values.Value;

import java.util.List;

public class Minus implements Value, Primitive {
    public Value apply(List<Value> args) throws IllegalArgumentException {
        if (args.size() == 0)
            return new SchemeNum(0);
        if (!(args.get(0) instanceof SchemeNum))
            throw new IllegalArgumentException("Type exception: "+
                    args.get(0)+" is not a number!");
        SchemeNum res = (SchemeNum) args.remove(0);
        for (Value v : args) {
            if (!(v instanceof SchemeNum))
                throw new IllegalArgumentException("Type exception: "+
                        v+" is not a number!");
            SchemeNum n = (SchemeNum) v;
            res = res.subtract(n);
        }
        return res;
    }

    public String toString() {
        return "PrimitivePlus";
    }
}

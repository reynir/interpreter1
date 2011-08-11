package interpreter.values.primitives;

import interpreter.values.*;
import java.util.List;

public class TypeOf {
    private static void check(List<Value> args) throws IllegalArgumentException {
        if (args.size() != 1)
            throw new IllegalArgumentException("Wrong number of arguments");
    }
    public static class IsProcedure implements Value, Primitive {
        public Value apply(List<Value> args) throws IllegalArgumentException {
            check(args);
            Value v = args.get(0);
            return SchemeBoolean.make((v instanceof CompoundProcedure) || (v instanceof Primitive));
        }
    }

    public static class IsPrimitive implements Value, Primitive {
        public Value apply(List<Value> args) throws IllegalArgumentException {
            check(args);
            Value v = args.get(0);
            return SchemeBoolean.make((v instanceof Primitive));
        }
    }

    public static class IsPair implements Value, Primitive {
        public Value apply(List<Value> args) throws IllegalArgumentException {
            check(args);
            Value v = args.get(0);
            return SchemeBoolean.make(v instanceof Pair);
        }
    }

    public static class IsNumber implements Value, Primitive {
        public Value apply(List<Value> args) throws IllegalArgumentException {
            check(args);
            Value v = args.get(0);
            return SchemeBoolean.make(v instanceof SchemeString);
        }
    }
}

package interpreter.values.primitives;

public class TypeOf {
    private static void check(List<Value> args) throws IllegalArgumentException {
        if (args.size() != 1)
            throw new IllegalArgumentException("Wrong number of arguments);
    }
    public static class IsProcedure implements Value, Primitive throws IllegalArgumentException {
        public Value apply(List<Value> args) {
            check(args);
            Value v = args.get(0);
            return (v instanceof Procedure) || (v instanceof Primitive);
        }
    }

    public static class IsPrimitive implements Value, Primitive throws IllegalArgumentException {
        public Value apply(List<Value> args) {
            check(args);
            Value v = args.get(0);
            return (v instanceof Primitive);
        }
    }

    public static class IsPair implements Value, Primitive throws IllegalArgumentException {
        public Value apply(List<Value> args) {
            check(args);
            Value v = args.get(0);
            return v instanceof Pair;
        }
    }

    public static class IsNumber implements Value, Primitive throws IllegalArgumentException {
        public Value apply(List<Value> args) {
            check(args);
            Value v = args.get(0);
            return v instanceof SchemeString;
        }
    }
}

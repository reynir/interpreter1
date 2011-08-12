package interpreter.values;

import java.util.List;

public class LambdaFormals {
    public enum Type {VAR_ARGS, FIXED_ARGS, NO_ARGS};
    public final Type type;
    private final List<SchemeIdentifier> fixedArgs;
    private final SchemeIdentifier varArgs;

    public LambdaFormals(List<SchemeIdentifier> args) {
        type = Type.FIXED_ARGS;
        fixedArgs = args;
        varArgs = null;
    }

    public LambdaFormals(SchemeIdentifier args) {
        type = Type.VAR_ARGS;
        varArgs = args;
        fixedArgs = null;
    }

    public LambdaFormals() {
        type = Type.NO_ARGS;
        varArgs = null;
        fixedArgs = null;
    }

    public Type getType() {
        return type;
    }

    public SchemeIdentifier getVarArgs() {
        return varArgs;
    }

    public List<SchemeIdentifier> getFixedArgs() {
        return fixedArgs;
    }

    public String toString() {
        if (type == Type.VAR_ARGS)
            return varArgs.toString();
        if (type == Type.NO_ARGS)
            return "()";
        StringBuilder res = new StringBuilder();
        res.append("(");
        for (SchemeIdentifier param : fixedArgs)
            res.append(param.toString());
        res.append(")");
        return res.toString();
    }
}

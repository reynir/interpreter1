import java.util.List;

public class LambdaFormals {
    public enum Type {VAR_ARGS, FIXED_ARGS};
    private Type type;
    private List<SchemeIdentifier> fixedArgs;
    private SchemeIdentifier varArgs;

    public LambdaFormals(List<SchemeIdentifier> args) {
        type = Type.FIXED_ARGS;
        fixedArgs = args;
    }

    public LambdaFormals(SchemeIdentifier args) {
        type = Type.VAR_ARGS;
        varArgs = args;
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
}

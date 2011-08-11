package interpreter.values;
import interpreter.Frame;

import java.util.List;
import java.util.ArrayList;

public class CompoundProcedure implements Value {
    private Frame env;
    private List<Value> body;
    private LambdaFormals params;

    public CompoundProcedure(Lambda l, Frame env) {
        params = l.getFormals();
        body = l.getBody();
        this.env = env;
    }

    public CompoundProcedure(List<Value> body, Frame env) {
        this.env = env;
        this.body = body;
        params = new LambdaFormals();
    }

    public CompoundProcedure(Value body, Frame env) {
        this.env = env;
        this.body = new ArrayList();
        this.body.add(body);
        params = new LambdaFormals();
    }

    public Frame getEnvironment(List<Value> args) {
        switch(params.type) {
            case VAR_ARGS: 
                {
                    List<SchemeIdentifier> vars = new ArrayList<SchemeIdentifier>();
                    vars.add(params.getVarArgs());
                    List<Value> vals = new ArrayList<Value>();
                    vals.add(Pair.makeConsList(args));
                    return new Frame(env, vars, vals);
                }
            case FIXED_ARGS: 
                {
                    List<SchemeIdentifier> vars = params.getFixedArgs();
                    if (vars.size() != args.size())
                        throw new IllegalArgumentException("Wrong number of arguments to procedure");
                    return new Frame(env, vars, args);
                }
            case NO_ARGS:
                if (args.size() > 0)
                    throw new IllegalArgumentException("Wrong number of arguments to procedure");
                return env;
            default:
                throw new RuntimeException("LambdaFormals type "+params.type+" is not implemented");
        }
    }

    public List<Value> getBody() {
        return body;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("CompoundProcedure[");
        res.append(params.toString());
        for (Value v : body) {
            res.append(", ");
            res.append(v.toString());
        }
        res.append("]");
        return res.toString();
    }
}

package interpreter.values;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class Lambda implements Value {
    private final LambdaFormals params;
    private final List<Value> body;

    public Lambda(LambdaFormals params, Value first, Value... rest) {
        this.params = params;
        body = new LinkedList<Value>();

        body.add(first);
        body.addAll(Arrays.asList(rest));
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("(lambda ");
        res.append(params.toString());
        for (Value v : body) {
            res.append(" ");
            res.append(v.toString());
        }
        res.append(")");
        return res.toString();
    }

    public LambdaFormals getFormals() {
        return params;
    }

    public List<Value> getBody() {
        return body;
    }
}

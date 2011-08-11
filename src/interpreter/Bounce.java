package interpreter;

import interpreter.values.Procedure;
import interpreter.values.Value;
import java.util.ArrayList;

public class Bounce implements Value {
    private final Value proc;
    
    public Bounce(Value proc) {
    	this.proc = proc;
    }
    
    public Value getProcedure() {
    	return proc;
    }

    public static final Value trampoline(Value x) {
        while (x instanceof Bounce) {
            x = Evaluator.apply(((Bounce)x).getProcedure(), new ArrayList<Value>());
        }
        return x;
    }

    public String toString() {
        return String.format("Bounce[%s]", proc);
    }
}

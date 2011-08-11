package interpreter;

import interpreter.values.Procedure;

public class Bounce {
    private final Procedure proc;
    
    public Bounce(Procedure proc) {
    	this.proc = proc;
    }
    
    public Procedure getProcedure() {
    	return proc;
    }
}

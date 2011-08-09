package interpreter;
import interpreter.values.*;
import interpreter.values.primitives.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Frame {
    private final Map<SchemeIdentifier, Value> bindings;
    private final Frame enclosingFrame;

    public Frame(Frame enclosing, List<SchemeIdentifier> vars, List<Value> vals) {
        if (vars.size() != vals.size()) {
            // TODO throw exception
        }

        this.bindings = new HashMap<SchemeIdentifier, Value>(vars.size());
        this.enclosingFrame = enclosing;


        for (int i=0; i < vars.size(); i++) {
            bindings.put(vars.get(i), vals.get(i));
        }
    }

    /** Top level frame. */
    public Frame() {
        this.bindings = new HashMap<SchemeIdentifier, Value>();
        this.enclosingFrame = null;

        addBinding("+", new Plus());
        addBinding("bob", new SchemeSymbol("Hej-bob"));
        addBinding("null", Null.NULL);
        addBinding("cons", new Cons());
        addBinding("car", new Car());
        addBinding("cdr", new Cdr());
        addBinding(">", new Compare.GreaterThan());
        addBinding("<", new Compare.LessThan());
        addBinding("=", new Compare.Equal());
    }

    public void setBang(SchemeIdentifier id, Value v) {
        if (bindings.containsKey(id)) {
            bindings.put(id, v);
        } else if (enclosingFrame != null) {
            enclosingFrame.setBang(id, v);
        } else {
            // throw new Exception("Foo is not bound"); // FIXME
        }
    }

    public Value getBindingValue(SchemeIdentifier id) {
        Value v = bindings.get(id);

        if (v != null) {
            return v;
        } else if (enclosingFrame != null) {
            return enclosingFrame.getBindingValue(id);
        } else {
            // throw new Exception("Foo is not bound"); // FIXME
            System.err.println("Warning: "+id+" is not bound"); //FIXME
            return null; // dummy
        }
    }

    public void addBinding(SchemeIdentifier id, Value v) {
        if (bindings.containsKey(id))
            System.err.println("Warning: Foo is already bound"); //FIXME
        bindings.put(id, v);

    }

    public void addBinding(String id, Value v) {
        addBinding(new SchemeIdentifier(id), v);
    }

    /** @return returns null if there's no enclosing frame */
    public Frame getEnclosingFrame() {
        return enclosingFrame;
    }

    // FIXME better name? Even necessary??
    public Frame getDefinitionFrame() {
        Frame res = this;
        Frame cur = res;
        while ((cur = cur.getEnclosingFrame()) != null) {
            res = cur;
        }

        return res;
    }
}

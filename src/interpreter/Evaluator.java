package interpreter;
import interpreter.values.*;

import java.util.List;
import java.util.ArrayList;

public class Evaluator {
    public static Value repl(Value prg, Frame env) {
        return Bounce.trampoline(eval(prg, env));
    }

    public static Value eval(Value exp, Frame env) {
        Value ret;

        if (isSelfEvaluating(exp)) {
            ret = exp;
        } else if (isQuoted(exp)) {
            ret = getQuotationText(exp);
        } else if (isVariable(exp)) {
            ret = env.getBindingValue((SchemeIdentifier) exp);
        } else if (isDefinition(exp)) {
            ret = evalDefinition(exp, env);
        } else if (isAssignment(exp)) {
            ret = evalAssignment(exp, env);
        } else if (isLambda(exp)) {
            ret = new CompoundProcedure((Lambda) exp, env);
        } else if (isIf(exp)) {
            If e = (If) exp;
            Value v = eval(e.getTest(), env);
            if (SchemeBoolean.isTrue(v)) {
                return new Bounce(new CompoundProcedure(e.getConsequent(), env));
            } else {
                return new Bounce(new CompoundProcedure(e.getAlternative(), env));
            }
        } else if (isConditional(exp)) {
            ret = evalConditional(exp, env); // Maybe getConditionalClauses(exp);
        } else if (isApplication(exp)) {
            ret = apply(eval(getOperator(exp), env),
                    getListOfValues(getOperands(exp), env));
        } else if (exp instanceof Tail) {
            Tail t = (Tail) exp;
            return new Bounce(new CompoundProcedure(t.getBody(), env));
        } else {
            throw new RuntimeException(String.format("Unknown expression: %s", exp));
        }

        return ret;
    }

    public static void repl() {
    }

    /* TODO: more self-evaluating types */
    public static boolean isSelfEvaluating(Value exp) { 
        if (exp instanceof SchemeNum ||
                exp instanceof SchemeString ||
                exp instanceof SchemeBoolean) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isQuoted(Value exp) {
        return exp instanceof Quote;
    }

    public static boolean isVariable(Value exp) {
        return exp instanceof SchemeIdentifier;
    }

    public static boolean isDefinition(Value exp) {
        return exp instanceof Define;
    }

    public static boolean isAssignment(Value exp) {
        return exp instanceof SetBang;
    }

    public static boolean isLambda(Value exp) {
        return exp instanceof Lambda;
    }

    public static boolean isIf(Value exp) {
        return exp instanceof If;
    }

    public static boolean isConditional(Value exp) {
        return false; // FIXME
    }

    public static boolean isApplication(Value exp) {
        if (!(exp instanceof Pair))
            return false;
        return ((Pair) exp).isProperList();
    }

    public static Value getQuotationText(Value exp) {
        Quote q = (Quote) exp;
        return q.getQuotationText();
    }

    public static Value evalDefinition(Value exp, Frame env) {
    	Define d = (Define) exp;
    	Value v = eval(d.getBody(), env);
    	env.addBinding(d.getIdentifier(), v);
    	return v;
    }

    public static Value evalAssignment(Value exp, Frame env) {
        SetBang s = (SetBang) exp;
        Value v = eval(s.getBody(), env);
        env.setBang(s.getIdentifier(), v);
        return v;
    }

    public static Value evalConditional(Value exp, Frame env) {
        return new SchemeSymbol("Conditionals-not-yet-implemented");
    }

    public static Value apply(Value operator, List<Value> args) {
        if (operator instanceof Primitive) {
            /* Trampoline any Bounces */
            for (int i=0; i < args.size(); i++) {
                Value v = args.get(i);
                if (v instanceof Bounce) {
                    args.remove(i);
                    args.add(i, Bounce.trampoline(v));
                }
            }
            return ((Primitive) operator).apply(args);
        }
        if (!(operator instanceof CompoundProcedure))
            throw new IllegalArgumentException("Trying to apply a non-procedure");
        CompoundProcedure proc = (CompoundProcedure) operator;
        Value ret = evalSequence(proc.getBody(), proc.getEnvironment(args));
        return ret;
    }

    public static Value evalSequence(List<Value> body, Frame env) {
        int i;
        for (i = 0; i < body.size()-1; i++) {
            eval(body.get(0), env);
        }

        return eval(body.get(i), env);
    }

    public static List<Value> getListOfValues(List<Value> exps, Frame env) {
        List<Value> res = new ArrayList<Value>(exps.size());

        for (Value v : exps) {
            if (v instanceof Bounce) {
                res.add(Bounce.trampoline(v));
            } else {
                res.add(eval(v, env));
            }
        }

        return res;
    }

    public static List<Value> getOperands(Value exp) {
        Pair c = (Pair) exp;
        Value v = c.cdr();
        if (v instanceof Null)
            return new ArrayList<Value>();
        c = (Pair) v;
        return c.toList();
    }

    public static Value getOperator(Value exp) {
        Pair c = (Pair) exp;
        return c.car();
    }
}

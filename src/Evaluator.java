import java.util.List;
import java.util.ArrayList;

public class Evaluator {

    public static Value eval(Value exp, Frame env) {
        if (isSelfEvaluating(exp)) {
            return exp;
        } else if (isQuoted(exp)) {
            return getQuotationText(exp);
        } else if (isVariable(exp)) {
            Value v = env.getBindingValue((SchemeIdentifier) exp);
            return env.getBindingValue((SchemeIdentifier) exp);
        } else if (isDefinition(exp)) {
            return evalDefinition(exp, env);
        } else if (isAssignment(exp)) {
            return evalAssignment(exp, env);
        } else if (isIf(exp)) {
            return evalIf(exp, env);
        } else if (isConditional(exp)) {
            return evalConditional(exp, env); // Maybe getConditionalClauses(exp);
        } else if (isApplication(exp)) {
            return apply(eval(getOperator(exp), env),
                    getListOfValues(getOperands(exp), env));
        } else {
            // throw new UnknownExpressionTypeException(String.format("Unknown expression: ~s", exp));
            return new SchemeSymbol("This should have been an exception");
        }
    }

    public static void repl() {
    }

    /* TODO: more self-evaluating types */
    public static boolean isSelfEvaluating(Value exp) { if (exp instanceof SchemeNum ||
                exp instanceof SchemeString) {
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
        return false; // FIXME
    }

    public static boolean isAssignment(Value exp) {
        return false; // FIXME
    }

    public static boolean isIf(Value exp) {
        return false; // FIXME
    }

    public static boolean isConditional(Value exp) {
        return false; // FIXME
    }

    public static boolean isApplication(Value exp) {
        if (!(exp instanceof Cons))
            return false;
        // maybe reserved keywords check?
        return ((Cons) exp).isProperList();
    }

    public static Value getQuotationText(Value exp) {
        Quote q = (Quote) exp;
        return q.getQuotationText();
    }

    public static Value evalDefinition(Value exp, Frame env) {
        return new SchemeSymbol("Definitions-not-yet-implemented");
    }

    public static Value evalAssignment(Value exp, Frame env) {
        return new SchemeSymbol("Assignments-not-yet-implemented");
    }

    public static Value evalIf(Value exp, Frame env) {
        return new SchemeSymbol("Ifs-not-yet-implemented");
    }

    public static Value evalConditional(Value exp, Frame env) {
        return new SchemeSymbol("Conditionals-not-yet-implemented");
    }

    public static Value apply(Value operator, List<Value> operands) {
        if (operator instanceof Primitive)
            return ((Primitive) operator).apply(operands);
        return new
            SchemeSymbol("Procedure-applications-not-yet-implemented");
    }

    public static List<Value> getListOfValues(List<Value> exps, Frame env) {
        List<Value> res = new ArrayList<Value>(exps.size());

        for (Value v : exps) {
            res.add(eval(v, env));
        }

        return res;
    }

    public static List<Value> getOperands(Value exp) {
        Cons c = (Cons) exp;
        Value v = c.cdr();
        if (v instanceof Null)
            return new ArrayList<Value>();
        c = (Cons) v;
        return c.toList();
    }

    public static Value getOperator(Value exp) {
        Cons c = (Cons) exp;
        return c.car();
    }
}

import java.util.List;

public class Evaluator {

    public static Value eval(Value exp, Frame env) {
        if (isSelfEvaluating(exp)) {
            return exp;
        } else if (isQuoted(exp)) {
            return getQuotationText(exp);
        } else if (isVariable(exp)) {
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
            return new SchemeIdentifier("This should have been an exception");
        }
    }

    public static void repl() {
    }

    /* TODO: more self-evaluating types */
    public static boolean isSelfEvaluating(Value exp) {
        if (exp instanceof SchemeNum ||
                exp instanceof SchemeString) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isQuoted(Value exp) {
        if (!(exp instanceof Cons))
            return false;
        Cons quote = (Cons) exp;
        if (quote.car() instanceof Quote) {
            if (!(quote.cdr() instanceof Cons))
                return false; //FIXME exception
            return ((Cons) quote.cdr()).isProperListOfLength(1);
        } else {
            return false;
        }
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
        Cons fst = (Cons) exp;
        Cons rst = (Cons) fst.cdr();
        return rst.car();
    }

    public static Value evalDefinition(Value exp, Frame env) {
        return new SchemeIdentifier("Definitions-not-yet-implemented");
    }

    public static Value evalAssignment(Value exp, Frame env) {
        return new SchemeIdentifier("Assignments-not-yet-implemented");
    }

    public static Value evalIf(Value exp, Frame env) {
        return new SchemeIdentifier("Ifs-not-yet-implemented");
    }

    public static Value evalConditional(Value exp, Frame env) {
        return new SchemeIdentifier("Conditionals-not-yet-implemented");
    }

    public static Value apply(Value operator, List<Value> operands) {
        return new
            SchemeIdentifier("Procedure-applications-not-yet-implemented");
    }

    // FIXME
    public static List<Value> getListOfValues(List<Value> exps, Frame env) {
        return null;
    }

    public static List<Value> getOperands(Value exp) {
        return null;
    }

    public static Value getOperator(Value exp) {
        return null;
    }
}

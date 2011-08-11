package interpreter.values;
import java.util.List;

public interface Primitive extends Value, Procedure {
    public Value apply(List<Value> args) throws IllegalArgumentException;
}

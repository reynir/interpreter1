package interpreter.values;
import java.util.List;

public interface Primitive extends Value {
    public Value apply(List<Value> args) throws IllegalArgumentException;
}

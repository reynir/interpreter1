package interpreter.values.primitives;

import java.util.List;

import interpreter.values.Pair;
import interpreter.values.Primitive;
import interpreter.values.Value;

public class Cdr implements Primitive, Value {

	@Override
	public Value apply(List<Value> args) throws IllegalArgumentException {
		if (args.size() != 1)
			throw new IllegalArgumentException("Car: Wrong number of arguments");
		Value v = args.get(0);
		if (!(v instanceof Pair))
			throw new IllegalArgumentException("Car: Wrong type");
		return ((Pair) v).cdr();
	}

}

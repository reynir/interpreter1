package interpreter.values.primitives;

import interpreter.values.Pair;
import interpreter.values.Primitive;
import interpreter.values.Value;

import java.util.List;

public class Cons implements Value, Primitive {

	@Override
	public Value apply(List<Value> args) throws IllegalArgumentException {
		if (args.size() != 2)
			throw new IllegalArgumentException(
					args.size() > 2 ? "Cons: Too many arguments"
							: "Cons: Too few arguments");
		Value car = args.get(0);
		Value cdr = args.get(1);

		return new Pair(car, cdr);
	}

}

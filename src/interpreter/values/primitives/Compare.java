package interpreter.values.primitives;

import java.util.List;

import interpreter.values.Primitive;
import interpreter.values.SchemeBoolean;
import interpreter.values.SchemeNum;
import interpreter.values.Value;

public class Compare {
	
	public static class GreaterThan implements Value, Primitive {
		public Value apply(List<Value> args) throws IllegalArgumentException {
			areTwoSchemeNums(args);
			SchemeNum first = (SchemeNum) args.get(0);
			SchemeNum second = (SchemeNum) args.get(1);
			return SchemeBoolean.make(first.greaterThan(second));
		}
	}
	
	public static class LessThan implements Value, Primitive {
		public Value apply(List<Value> args) throws IllegalArgumentException {
			areTwoSchemeNums(args);
			SchemeNum first =  (SchemeNum) args.get(0);
			SchemeNum second = (SchemeNum) args.get(1);
			return SchemeBoolean.make(second.greaterThan(first));
		}
	}
	
	public static class Equal implements Value, Primitive {
		public Value apply(List<Value> args) throws IllegalArgumentException {
			areTwoSchemeNums(args);
			SchemeNum first =  (SchemeNum) args.get(0);
			SchemeNum second = (SchemeNum) args.get(1);
			return SchemeBoolean.make(!first.greaterThan(second) && !second.greaterThan(first));
		}
	}
	
	public static boolean areTwoSchemeNums(List<Value> args) throws IllegalArgumentException {
		if (args.size() != 2)
			throw new IllegalArgumentException("Wrong number of arguments");
		Value fst = args.get(0);
		Value snd = args.get(1);
		if (!(fst instanceof SchemeNum) || !(snd instanceof SchemeNum))
			throw new IllegalArgumentException("Wrong type, yo");
		return true;
	}
}

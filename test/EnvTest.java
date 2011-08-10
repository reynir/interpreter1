import interpreter.Frame;
import interpreter.values.SchemeIdentifier;
import interpreter.values.SchemeSymbol;



public class EnvTest {
    public static void main(String... args) {
        Frame env = new Frame();
        SchemeSymbol b = new SchemeSymbol("Bob");

        env.addBinding(make("bob"), b);

        System.out.println(env.getBindingValue(make("bob")));
    }

    public static SchemeIdentifier make(String s) {
        return new SchemeIdentifier(s);
    }
}

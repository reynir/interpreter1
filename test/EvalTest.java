import interpreter.Evaluator;
import interpreter.Frame;
import interpreter.Tokenizer;
import interpreter.values.Value;

import java.io.EOFException;
import java.io.StringReader;

public class EvalTest {

    public static void main(String... args) {
        Tokenizer t;
        if (args.length == 0)
            t = new Tokenizer(System.in);
        else
            t = new Tokenizer(new StringReader(args[0]));
        Value res;
        Frame env = new Frame();
        try {
            while (true) {
                try {
                    System.out.print("$$ ");
                    System.out.flush();
                    res = t.nextValue();
                    System.out.println(Evaluator.repl(res, env));
                } catch(StackOverflowError e) {
                    System.out.println("Stack overflow!");
                    e.printStackTrace();
                }
            }
        } catch(EOFException e) {
            // ignore
        } catch (StackOverflowError e) {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

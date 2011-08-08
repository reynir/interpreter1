import java.io.EOFException;

public class EvalTest {

    public static void main(String... args) {
        Tokenizer t = new Tokenizer(System.in);
        Evaluator eval = new Evaluator();
        Value res;
        Frame env = new Frame();
        try {
            while (true) {
                System.out.print("$$ ");
                System.out.flush();
                res = t.nextValue();
                System.out.println(eval.eval(res, env));
            }
        } catch(EOFException e) {
            // ignore
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

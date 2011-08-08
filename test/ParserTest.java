import java.io.EOFException;

public class ParserTest {
    public static void main(String... args) {
        Tokenizer t = new Tokenizer(System.in);
        try {
            while (true) {
                System.out.print("$$ ");
                System.out.flush();
                System.out.println(t.nextValue());
            }
        } catch(EOFException e) {
            // ignore
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class Lambda implements Value {
    private static LambdaFormals params;
    private static List<Value> body;

    public Lambda(LambdaFormals params, Value first, Value... rest) {
        this.params = params;
        body = new LinkedList<Value>();

        body.add(first);
        body.addAll(Arrays.asList(rest));
    }
}

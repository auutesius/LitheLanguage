package Array;
import javassist.gluonj.util.Loader;
import Closure.ClosureEvaluator;
import Native.NativeEvaluator;
import LitheClass.ClassEvaluator;
import LitheClass.ClassInterpreter;

public class ArrayRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class,
                   ArrayEvaluator.class, NativeEvaluator.class,
                   ClosureEvaluator.class);
    }
}

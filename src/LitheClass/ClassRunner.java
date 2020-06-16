package LitheClass;
import javassist.gluonj.util.Loader;
import Closure.ClosureEvaluator;
import Native.NativeEvaluator;

public class ClassRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class,
                   NativeEvaluator.class, ClosureEvaluator.class);
    }
}

package StaticType;

import javassist.gluonj.util.Loader;
import Native.NativeEvaluator;

public class InferRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(TypedInterpreter.class, args, InferFuncTypes.class,
                                                 NativeEvaluator.class);
    }
}

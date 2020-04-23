package Closure;
import java.util.List;
import javassist.gluonj.*;
import LitheCore.ast.ASTree;
import LitheCore.ast.Fun;
import BasicRunner.Environment;

@Require(FuncEvaluator.class)
@Reviser public class ClosureEvaluator {
    @Reviser public static class FunEx extends Fun {
        public FunEx(List<ASTree> c) { super(c); }
        public Object eval(Environment env) {
            return new Function(parameters(), body(), env);
        }
    }
}

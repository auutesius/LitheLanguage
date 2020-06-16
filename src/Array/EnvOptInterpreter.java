package Array;
import BasicRunner.BasicEvaluator;
import BasicRunner.Environment;
import Native.Natives;
import LitheXCore.BasicParser;
import LitheXCore.ClosureParser;
import LitheXCore.CodeDialog;
import LitheXCore.Lexer;
import LitheXCore.ParseException;
import LitheXCore.Token;
import LitheXCore.ast.ASTree;
import LitheXCore.ast.NullStmnt;

public class EnvOptInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(),
            new Natives().environment(new ResizableArrayEnv()));
    }
    public static void run(BasicParser bp, Environment env)
        throws ParseException
    {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree t = bp.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                ((EnvOptimizer.ASTreeOptEx)t).lookup(
                        ((EnvOptimizer.EnvEx2)env).symbols());
                Object r = ((BasicEvaluator.ASTreeEx)t).eval(env);
                System.out.println("=> " + r);
            }
        }
    }
}

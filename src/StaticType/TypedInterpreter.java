package StaticType;
import LitheCore.BasicParser;
import LitheCore.CodeDialog;
import LitheCore.Lexer;
import LitheCore.Token;
import LitheCore.TypedParser;
import LitheCore.ParseException;
import LitheCore.ast.ASTree;
import LitheCore.ast.NullStmnt;
import Array.EnvOptimizer;
import Array.ResizableArrayEnv;
import BasicRunner.BasicEvaluator;
import BasicRunner.Environment;

public class TypedInterpreter {
    public static void main(String[] args) throws ParseException, TypeException {
        TypeEnv te = new TypeEnv();
        run(new TypedParser(),
            new TypedNatives(te).environment(new ResizableArrayEnv()),
            te);
    }
    public static void run(BasicParser bp, Environment env, TypeEnv typeEnv)
        throws ParseException, TypeException
    {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree tree = bp.parse(lexer);
            if (!(tree instanceof NullStmnt)) {
                ((EnvOptimizer.ASTreeOptEx)tree).lookup(
                                        ((EnvOptimizer.EnvEx2)env).symbols());
                TypeInfo type
                    = ((TypeChecker.ASTreeTypeEx)tree).typeCheck(typeEnv);
                Object r = ((BasicEvaluator.ASTreeEx)tree).eval(env);
                System.out.println("=> " + r + " : " + type);
            }
        }
    }
}

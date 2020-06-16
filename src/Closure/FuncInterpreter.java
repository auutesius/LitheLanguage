package Closure;
import LitheXCore.FuncParser;
import LitheXCore.ParseException;
import BasicRunner.BasicInterpreter;

public class FuncInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new FuncParser(), new NestedEnv());
    }
}

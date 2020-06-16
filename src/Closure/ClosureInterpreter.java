package Closure;
import LitheXCore.ClosureParser;
import LitheXCore.ParseException;
import BasicRunner.BasicInterpreter;

public class ClosureInterpreter extends BasicInterpreter{
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new NestedEnv());
    }
}

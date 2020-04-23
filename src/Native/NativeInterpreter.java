package Native;
import LitheCore.ClosureParser;
import LitheCore.ParseException;
import BasicRunner.BasicInterpreter;
import Closure.NestedEnv;

public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(),
            new Natives().environment(new NestedEnv()));
    }
}

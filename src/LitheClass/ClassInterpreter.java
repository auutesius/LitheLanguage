package LitheXClass;
import LitheXCore.ClassParser;
import LitheXCore.ParseException;
import BasicRunner.BasicInterpreter;
import Closure.NestedEnv;
import Native.Natives;

public class ClassInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClassParser(), new Natives().environment(new NestedEnv())); 
    }
}

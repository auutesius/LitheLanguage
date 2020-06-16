package Optimization;
import LitheCore.ClassParser;
import LitheCore.ParseException;
import Array.EnvOptInterpreter;
import Array.ResizableArrayEnv;
import Native.Natives;

public class ObjOptInterpreter extends EnvOptInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClassParser(),
            new Natives().environment(new ResizableArrayEnv()));
    }
}

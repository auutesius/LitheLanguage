package LVM;
import LitheXCore.FuncParser;
import LitheXCore.ParseException;
import Array.EnvOptInterpreter;
import Native.Natives;

public class VmInterpreter extends EnvOptInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new FuncParser(),
            new Natives().environment(new LitheXVMEnv(100000, 100000, 1000)));
    }
}

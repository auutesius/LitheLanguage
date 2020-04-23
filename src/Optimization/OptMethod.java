package Optimization;
import LitheCore.ast.BlockStmnt;
import LitheCore.ast.ParameterList;
import Array.ArrayEnv;
import Array.OptFunction;
import BasicRunner.Environment;

public class OptMethod extends OptFunction {
    OptLitheObject self;
    public OptMethod(ParameterList parameters, BlockStmnt body,
                     Environment env, int memorySize, OptLitheObject self)
    {
        super(parameters, body, env, memorySize);
        this.self = self;
    }
    @Override public Environment makeEnv() {
        ArrayEnv e = new ArrayEnv(size, env);
        e.put(0, 0, self);
        return e;
    }
}

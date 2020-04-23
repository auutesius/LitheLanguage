package LVM;
import LitheCore.ast.BlockStmnt;
import LitheCore.ast.ParameterList;
import BasicRunner.Environment;
import Closure.Function;

public class VmFunction extends Function {
    protected int entry;
    public VmFunction(ParameterList parameters, BlockStmnt body,
                      Environment env, int entry)
    {
        super(parameters, body, env);
        this.entry = entry;
    }
    public int entry() { return entry; }
}

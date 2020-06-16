package StaticType;
import java.util.List;
import StaticType.TypeInfo.FunctionType;
import StaticType.TypeInfo.UnknownType;
import StaticType.InferTypes.UnknownTypeEx;
import LitheXCore.ast.ASTree;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

@Require({TypeChecker.class, InferTypes.class})
@Reviser public class InferFuncTypes {
    @Reviser public static class DefStmntEx3 extends TypeChecker.DefStmntEx2 {
        public DefStmntEx3(List<ASTree> c) { super(c); }
        @Override public TypeInfo typeCheck(TypeEnv tenv) throws TypeException {
            FunctionType func = super.typeCheck(tenv).toFunctionType();
            for (TypeInfo t: func.parameterTypes)
                fixUnknown(t);
            fixUnknown(func.returnType);
            return func;
        }
        protected void fixUnknown(TypeInfo t) {
            if (t.isUnknownType()) {
                UnknownType ut = t.toUnknownType();
                if (!((UnknownTypeEx)ut).resolved())
                    ((UnknownTypeEx)ut).setType(TypeInfo.ANY);
            }
        }
    }
}

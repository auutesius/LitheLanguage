package Optimization;
import java.util.List;
import LitheXCore.LitheXException;
import LitheXCore.ast.ASTree;
import LitheXCore.ast.Dot;
import BasicRunner.Environment;
import javassist.gluonj.*;

@Require(ObjOptimizer.class)
@Reviser public class InlineCache {
    @Reviser public static class DotEx2 extends ObjOptimizer.DotEx {
        protected OptClassInfo classInfo = null;
        protected boolean isField;
        protected int index;
        public DotEx2(List<ASTree> c) { super(c); }
        @Override public Object eval(Environment env, Object value) {
            if (value instanceof OptLitheXObject) {
                OptLitheXObject target = (OptLitheXObject)value;
                if (target.classInfo() != classInfo)
                    updateCache(target);
                if (isField)
                    return target.read(index);
                else
                    return target.method(index);
            }
            else
                return super.eval(env, value);
        }
        protected void updateCache(OptLitheXObject target) {
            String member = name();
            classInfo = target.classInfo();
            Integer i = classInfo.fieldIndex(member);
            if (i != null) {
                isField = true;
                index = i;
                return;
            }
            i = classInfo.methodIndex(member);
            if (i != null) {
                isField = false;
                index = i;
                return;
            }
            throw new LitheXException("bad member access: " + member, this);
        }
    }
    @Reviser public static class AssignEx2 extends ObjOptimizer.AssignEx {
        protected OptClassInfo classInfo = null;
        protected int index;
        public AssignEx2(List<ASTree> c) { super(c); }
        @Override protected Object setField(OptLitheXObject obj, Dot expr,
                                            Object rvalue)
        {
            if (obj.classInfo() != classInfo) {
                String member = expr.name();
                classInfo = obj.classInfo();
                Integer i = classInfo.fieldIndex(member);
                if (i == null)
                    throw new LitheXException("bad member access: " + member,
                                             this);       
                index = i;
            }
            obj.write(index, rvalue);
            return rvalue;
        }
    }
}

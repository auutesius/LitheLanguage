package Optimization;
import java.util.ArrayList;
import java.util.List;
import static javassist.gluonj.GluonJ.revise;
import javassist.gluonj.*;
import LitheCore.*;
import LitheCore.ast.*;
import BasicRunner.Environment;
import BasicRunner.BasicEvaluator;
import BasicRunner.BasicEvaluator.ASTreeEx;
import Closure.FuncEvaluator.PrimaryEx;
import Array.ArrayEnv;
import Array.EnvOptimizer;
import Array.Symbols;
import Array.EnvOptimizer.ASTreeOptEx;
import Array.EnvOptimizer.EnvEx2;
import Array.EnvOptimizer.ParamsEx;
import Optimization.OptLitheObject.AccessException;

@Require(EnvOptimizer.class)
@Reviser public class ObjOptimizer {
    @Reviser public static class ClassStmntEx extends ClassStmnt {
        public ClassStmntEx(List<ASTree> c) { super(c); }
        public void lookup(Symbols syms) {}
        public Object eval(Environment env) {
            Symbols methodNames = new MemberSymbols(((EnvEx2)env).symbols(),
                                                    MemberSymbols.METHOD);
            Symbols fieldNames = new MemberSymbols(methodNames,
                                                   MemberSymbols.FIELD);
            OptClassInfo ci = new OptClassInfo(this, env, methodNames,
                                               fieldNames);
            ((EnvEx2)env).put(name(), ci);
            ArrayList<DefStmnt> methods = new ArrayList<DefStmnt>();
            if (ci.superClass() != null)
                ci.superClass().copyTo(fieldNames, methodNames, methods);
            Symbols newSyms = new SymbolThis(fieldNames);
            ((ClassBodyEx)body()).lookup(newSyms, methodNames, fieldNames,
                                         methods);
            ci.setMethods(methods);
            return name();
        }
    }
    @Reviser public static class ClassBodyEx extends ClassBody {
        public ClassBodyEx(List<ASTree> c) { super(c); }
        public Object eval(Environment env) {
            for (ASTree t: this)
                if (!(t instanceof DefStmnt))
                    ((ASTreeEx)t).eval(env);
            return null;
        }
        public void lookup(Symbols syms, Symbols methodNames,
                           Symbols fieldNames, ArrayList<DefStmnt> methods)
        {
            for (ASTree t: this) {
                if (t instanceof DefStmnt) {
                    DefStmnt def = (DefStmnt)t;
                    int oldSize = methodNames.size();
                    int i = methodNames.putNew(def.name());
                    if (i >= oldSize)
                        methods.add(def);
                    else
                        methods.set(i, def);
                    ((DefStmntEx2)def).lookupAsMethod(fieldNames);
                }
                else
                    ((ASTreeOptEx)t).lookup(syms);
            }
        }
    }
    @Reviser public static class DefStmntEx2 extends EnvOptimizer.DefStmntEx {
        public DefStmntEx2(List<ASTree> c) { super(c); }
        public int locals() { return size; }
        public void lookupAsMethod(Symbols syms) {
            Symbols newSyms = new Symbols(syms);
            newSyms.putNew(SymbolThis.NAME);
            ((ParamsEx)parameters()).lookup(newSyms);
            ((ASTreeOptEx)revise(body())).lookup(newSyms);
            size = newSyms.size();
        }
    }
    @Reviser public static class DotEx extends Dot {
        public DotEx(List<ASTree> c) { super(c); }
        public Object eval(Environment env, Object value) {
            String member = name();
            if (value instanceof OptClassInfo) {
                if ("new".equals(member)) {
                    OptClassInfo ci = (OptClassInfo)value;
                    ArrayEnv newEnv = new ArrayEnv(1, ci.environment());
                    OptLitheObject so = new OptLitheObject(ci, ci.size());
                    newEnv.put(0, 0, so);
                    initObject(ci, so, newEnv);
                    return so;
                }
            }
            else if (value instanceof OptLitheObject) {
                try {
                    return ((OptLitheObject)value).read(member);
                } catch (AccessException e) {}
            }
            throw new LitheException("bad member access: " + member, this);
        }
        protected void initObject(OptClassInfo ci, OptLitheObject obj,
                                  Environment env)
        {
            if (ci.superClass() != null)
                initObject(ci.superClass(), obj, env);
            ((ClassBodyEx)ci.body()).eval(env);
        }
    }
    @Reviser public static class NameEx2 extends EnvOptimizer.NameEx {
        public NameEx2(Token t) { super(t); }
        @Override public Object eval(Environment env) {
            if (index == UNKNOWN)
                return env.get(name());
            else if (nest == MemberSymbols.FIELD)
                return getThis(env).read(index);
            else if (nest == MemberSymbols.METHOD)
                return getThis(env).method(index);
            else
                return ((EnvEx2)env).get(nest, index);
        }
        @Override public void evalForAssign(Environment env, Object value) {
            if (index == UNKNOWN)
                env.put(name(), value);
            else if (nest == MemberSymbols.FIELD)
                getThis(env).write(index, value);
            else if (nest == MemberSymbols.METHOD)
                throw new LitheException("cannot update a method: " + name(),
                                         this);
            else
                ((EnvEx2)env).put(nest, index, value);
        }
        protected OptLitheObject getThis(Environment env) {
            return (OptLitheObject)((EnvEx2)env).get(0, 0);
        }
    }
    @Reviser public static class AssignEx extends BasicEvaluator.BinaryEx {
        public AssignEx(List<ASTree> c) { super(c); }
        @Override
        protected Object computeAssign(Environment env, Object rvalue) {
            ASTree le = left();
            if (le instanceof PrimaryExpr) {
                PrimaryEx p = (PrimaryEx)le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                    Object t = ((PrimaryEx)le).evalSubExpr(env, 1);
                    if (t instanceof OptLitheObject)
                        return setField((OptLitheObject)t, (Dot)p.postfix(0),
                                        rvalue);
                }
            }
            return super.computeAssign(env, rvalue);
        }
        protected Object setField(OptLitheObject obj, Dot expr, Object rvalue) {
            String name = expr.name();
            try {
                obj.write(name, rvalue);
                return rvalue;
            } catch (AccessException e) {
                throw new LitheException("bad member access: " + name, this);
            }
        }
    }
}

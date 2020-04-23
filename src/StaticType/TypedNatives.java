package StaticType;
import BasicRunner.Environment;
import Native.Natives;
import Array.EnvOptimizer.EnvEx2;

public class TypedNatives extends Natives {
    protected TypeEnv typeEnv;
    public TypedNatives(TypeEnv te) { typeEnv = te; }
    protected void append(Environment env, String name, Class<?> clazz,
                          String methodName, TypeInfo type, Class<?> ... params)
    {
        append(env, name, clazz, methodName, params);
        int index = ((EnvEx2)env).symbols().find(name);
        typeEnv.put(0, index, type);
    }
    protected void appendNatives(Environment env) {
        append(env, "print", StaticType.java.print.class, "m",
               TypeInfo.function(TypeInfo.INT, TypeInfo.ANY),
               Object.class);
        append(env, "read", StaticType.java.read.class, "m",
                TypeInfo.function(TypeInfo.STRING));
        append(env, "length", StaticType.java.length.class, "m",
               TypeInfo.function(TypeInfo.INT, TypeInfo.STRING),
               String.class);
        append(env, "toInt", StaticType.java.toInt.class, "m",
               TypeInfo.function(TypeInfo.INT, TypeInfo.ANY),
               Object.class);
        append(env, "currentTime", StaticType.java.currentTime.class, "m",
               TypeInfo.function(TypeInfo.INT)); 
    }
}

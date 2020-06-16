package StaticType;
import LitheXCore.LitheXException;
import Closure.Function;

public class JavaFunction extends Function {
    protected String className;
    protected Class<?> clazz;
    public JavaFunction(String name, String method, JavaLoader loader) {
        super(null, null, null);
        className = className(name);
        clazz = loader.load(className, method);
    }
    public static String className(String name) {
        return "chap14.java." + name;
    }
    public Object invoke(Object[] args) {
        try {
            return clazz.getDeclaredMethods()[0].invoke(null, args);
        } catch (Exception e) {
            throw new LitheXException(e.getMessage());
        }
    }
}

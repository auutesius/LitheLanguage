package StaticType;
import LitheCore.ast.ASTree;

public class TypeException extends Exception {
    public TypeException(String msg, ASTree t) {
        super(msg + " " + t.location()); 
    }
}

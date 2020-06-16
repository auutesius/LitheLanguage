package LitheCore;
import LitheCore.ast.ASTree;

public class LitheException extends RuntimeException {
    public LitheException(String m) { super(m); }
    public LitheException(String m, ASTree t) {
        super(m + " " + t.location());
    }
}

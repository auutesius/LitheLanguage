package LitheXCore;
import LitheXCore.ast.ASTree;

public class LitheXException extends RuntimeException {
    public LitheXException(String m) { super(m); }
    public LitheXException(String m, ASTree t) {
        super(m + " " + t.location());
    }
}

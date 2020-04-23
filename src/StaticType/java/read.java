package StaticType.java;
import Array.ArrayEnv;
import javax.swing.JOptionPane;

public class read {
    public static String m(ArrayEnv env) { return m(); }
    public static String m() {
        return JOptionPane.showInputDialog(null);
    }
}
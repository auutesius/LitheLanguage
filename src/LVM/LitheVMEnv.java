package LVM;
import Array.ResizableArrayEnv;

public class LitheXVMEnv extends ResizableArrayEnv implements HeapMemory {
    protected LitheXVM svm;
    protected Code code;
    public LitheXVMEnv(int codeSize, int stackSize, int stringsSize) {
        svm = new LitheXVM(codeSize, stackSize, stringsSize, this);
        code = new Code(svm);
    }
    public LitheXVM stoneVM() { return svm; }
    public Code code() { return code; }
    public Object read(int index) { return values[index]; }
    public void write(int index, Object v) { values[index] = v; }
}

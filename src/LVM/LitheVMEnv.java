package LVM;
import Array.ResizableArrayEnv;

public class LitheVMEnv extends ResizableArrayEnv implements HeapMemory {
    protected LitheVM svm;
    protected Code code;
    public LitheVMEnv(int codeSize, int stackSize, int stringsSize) {
        svm = new LitheVM(codeSize, stackSize, stringsSize, this);
        code = new Code(svm);
    }
    public LitheVM stoneVM() { return svm; }
    public Code code() { return code; }
    public Object read(int index) { return values[index]; }
    public void write(int index, Object v) { values[index] = v; }
}

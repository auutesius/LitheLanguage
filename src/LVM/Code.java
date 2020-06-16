package LVM;

public class Code {
    protected LitheXVM lvm;
    protected int codeSize;
    protected int numOfStrings;
    protected int nextReg;
    protected int frameSize;

    public Code(LitheXVM LitheXVm) {
        lvm = LitheXVm;
        codeSize = 0;
        numOfStrings = 0;
    }
    public int position() { return codeSize; }
    public void set(short value, int pos) {
        lvm.code()[pos] = (byte)(value >>> 8);
        lvm.code()[pos + 1] = (byte)value;
    }
    public void add(byte b) {
        lvm.code()[codeSize++] = b;
    }
    public void add(short i) {
        add((byte)(i >>> 8));
        add((byte)i);
    }
    public void add(int i) {
        add((byte)(i >>> 24));
        add((byte)(i >>> 16));
        add((byte)(i >>> 8));
        add((byte)i);
    }
    public int record(String s) {
        lvm.strings()[numOfStrings] = s;
        return numOfStrings++;
    }
}

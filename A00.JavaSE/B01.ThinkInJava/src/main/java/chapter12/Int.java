package chapter12;

public class Int {

    private int i;

    public Int(int ii) {
        this.i = ii;
    }

    public void increment(){
        i++;
    }

    public String toString(){
        return Integer.toString(i);
    }
}

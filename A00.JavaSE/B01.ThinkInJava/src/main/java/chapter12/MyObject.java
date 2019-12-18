package chapter12;

public class MyObject implements Cloneable {

    int i ;

    MyObject(int ii){
        i = ii;
    }

    public Object clone()  {
        Object o = null;
        try {
            o = super.clone();
        }catch (CloneNotSupportedException e){
            System.out.println("MyObject can't clone");
        }
        return o;
    }

    public String toString(){
        return Integer.toString(i);
    }
}

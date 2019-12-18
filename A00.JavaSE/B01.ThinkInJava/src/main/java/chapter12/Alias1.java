package chapter12;

public class Alias1 {

    int i;

    Alias1(int ii) {
        i = ii;
    }

    public static void main(String[] args) {
        Alias1 x = new Alias1(7);
        // 新建一个Alias1句柄，不是把它分配给由new创建的一个新对象，而是分配给一个现有的句柄。所以句柄x的内容-即对象x指向的地址---被分配给y
        Alias1 y = x;
        System.out.println("x:" + x.i);
        System.out.println("y:" + y.i);
        System.out.println("Incrementing x");
        x.i++;
        System.out.println("x:" + x.i);
        System.out.println("y:" + y.i);


    }


}

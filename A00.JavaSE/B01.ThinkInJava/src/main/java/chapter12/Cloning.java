package chapter12;

import java.util.Enumeration;
import java.util.Vector;

public class Cloning {
    public static void main(String[] args) {

        Vector v = new Vector();
        for (int i = 0; i < 10; i++) {
            v.addElement(new Int(i));
        }
        System.out.println("v:" + v);

        Vector v2 = (Vector) v.clone();
        for (Enumeration e = v2.elements(); e.hasMoreElements(); ) {
            ((Int) e.nextElement()).increment();
        }
        System.out.println("v:" + v);

        /**
         * v:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
         * v:[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
         * Vector的clone方法不能自动尝试克隆Vector内包含的每个对象--由于别名问题
         * 老的Vector和克隆的Vector都包含了相同的对象。我们通常把这种情况叫作“简单复制”或者“浅层复制”
         */

    }
}

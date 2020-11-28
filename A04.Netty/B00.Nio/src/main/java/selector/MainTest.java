package selector;

import java.nio.channels.SelectionKey;

public class MainTest {

    public static void main(String[] args) {

        System.out.println(SelectionKey.OP_READ & SelectionKey.OP_READ);
        System.out.println(SelectionKey.OP_READ & SelectionKey.OP_WRITE);


    }
}

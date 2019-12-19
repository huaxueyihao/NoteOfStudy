package buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

public class ByteBufferTest {


    @Test
    public void testByteBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(256);
        System.out.println(buffer.arrayOffset());
        System.out.println(buffer.putInt(10));
        System.out.println(buffer.putDouble(10));
        System.out.println(buffer.putLong(10));


        System.out.println(buffer.get());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());

        System.out.println(buffer.flip());

        System.out.println(buffer);

        System.out.println(buffer.reset());


    }

}

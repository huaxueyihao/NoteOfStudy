package channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {

    public static void main(String[] args) throws IOException {

        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);
        channelCopy1(source, dest);
//        channelCopy2(source, dest);
        source.close();
        dest.close();


    }

    private static void channelCopy2(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);

        while (source.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }
            buffer.clear();
        }
    }

    private static void channelCopy1(ReadableByteChannel source, WritableByteChannel dest) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);

        while (source.read(buffer) != -1) {
            buffer.flip();

            dest.write(buffer);

            buffer.compact();
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }


    }


}

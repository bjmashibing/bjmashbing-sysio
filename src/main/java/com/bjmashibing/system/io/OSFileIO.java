package com.bjmashibing.system.io;

import org.junit.Test;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class OSFileIO {

    static byte[] data = "123456789\n".getBytes();
    static String path =  "/root/testfileio/out.txt";


    public static void main(String[] args) throws Exception {

        switch ( args[0]) {
            case "0" :
                testBasicFileIO();
                break;
            case "1":
                testBufferedFileIO();
                break;
            case "2" :
                testRandomAccessFileWrite();
            case "3":
                whatByteBuffer();
            default:

        }
    }


    //最基本的file写
    @Test
    public static  void testBasicFileIO() throws Exception {
        File file = new File(path);
        FileOutputStream out = new FileOutputStream(file);
        while(true){
            Thread.sleep(10);
            out.write(data);

        }

    }

    //测试buffer文件IO
    @Test
    public static void testBufferedFileIO() throws Exception {
        File file = new File(path);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        while(true){
            Thread.sleep(10);
            out.write(data);
        }
    }



    //测试文件NIO

    @Test
    public static void testRandomAccessFileWrite() throws  Exception {


        RandomAccessFile raf = new RandomAccessFile(path, "rw");

        raf.write("hello mashibing\n".getBytes());
        raf.write("hello seanzhou\n".getBytes());
        System.out.println("write------------");
        System.in.read();

        raf.seek(4);
        raf.write("ooxx".getBytes());

        System.out.println("seek---------");
        System.in.read();

        FileChannel rafchannel = raf.getChannel();
        MappedByteBuffer map = rafchannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);
        map.put("@@@".getBytes());
        System.out.println("map--put--------");
        System.in.read();

//        map.force(); //  flush



        raf.seek(0);

        ByteBuffer buffer = ByteBuffer.allocate(8192);
//        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        int read = rafchannel.read(buffer);
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);

        for (int i = 0; i < buffer.limit(); i++) {
            Thread.sleep(200);
            System.out.print(((char)buffer.get(i)));
        }


    }


    @Test
    public static void whatByteBuffer(){

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("postition: " + buffer.position());
        System.out.println("limit: " +  buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("mark: " + buffer);

        buffer.put("123".getBytes());

        System.out.println("-------------put:123......");
        System.out.println("mark: " + buffer);

        buffer.flip();

        System.out.println("-------------flip......");
        System.out.println("mark: " + buffer);

        buffer.get();

        System.out.println("-------------get......");
        System.out.println("mark: " + buffer);

        buffer.compact();

        System.out.println("-------------compact......");
        System.out.println("mark: " + buffer);

        buffer.clear();

        System.out.println("-------------clear......");
        System.out.println("mark: " + buffer);

    }


}

package com.bjmashibing.system.io;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @author: 马士兵教育
 * @create: 2020-04-26 15:59
 */
public class NettyClient {

    public static void main(String[] args) {
        try {
            NioEventLoopGroup worker = new NioEventLoopGroup();
            Bootstrap boot = new Bootstrap();
            boot.group(worker)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("localhost", 9090)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            System.out.println("初始化client");
                            ChannelPipeline p = sc.pipeline();
                            p.addLast(new MyInbound());
                        }
                    });


            ChannelFuture conn = boot.connect().sync();


            Channel client = conn.channel();
            System.out.println(client);

            ByteBuf byteBuf = Unpooled.copiedBuffer("hello world".getBytes());
            client.writeAndFlush(byteBuf).sync();




        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

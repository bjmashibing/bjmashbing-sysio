



关于操作系统IO篇章

冯诺依曼体系结构：计算器，控制器，主存储器，输入设备，输出设备



```

常用软件：
yum install -y strace lsof  pmap tcpdump 

```



```
VFS:  虚拟文件系统 案例

[root@node01 ~]# df
Filesystem     1K-blocks     Used Available Use% Mounted on
/dev/sda3      202092480 10776508 181050220   6% /
tmpfs            1954400        0   1954400   0% /dev/shm
/dev/sda1         198337    27795    160302  15% /boot



df 
Filesystem     1K-blocks    Used Available Use% Mounted on
/dev/sda3      202092480 7187520 184639208   4% /
tmpfs            1954400       0   1954400   0% /dev/shm
/dev/sda1         198337   27795    160302  15% /boot

通过自己创建磁盘镜像文件，挂载到vfs目录中，进行目录文件操作：
dd if=/dev/zero   of=~/disk02.img bs=1048576 count=100
losetup /dev/loop0 ~/disk02.img
mke2fs  /dev/loop0
mkdir /mnt/ooxx
mount -t ext2 /dev/loop0 /mnt/ooxx
cd /mnt/ooxx
mkdir bin lib64
whereis bash
ldd /bin/bash
cp /bin/bash bin
cp /lib64/{libtinfo.so.5,libdl.so.2,libc.so.6,ld-linux-x86-64.so.2}  lib64
chroot ./
echo "aaa" > /abc.txt
exit
cat abc.txt
```

```
【抽时间一定要去实操，光看不够的】
测试pipeline类型：
{ echo $BASHPID ;  read x;  }  |  { cat ; echo $BASHPID ;  read y; } 
测试socket类型：
exec  8<>  /dev/tcp/www.baidu.com/80

lsof -op $$
【以下是整合的结果】
COMMAND  PID USER   FD   TYPE DEVICE OFFSET     NODE NAME
bash    4398 root  cwd    DIR    8,3        10227872 /root/mashibing
bash    4398 root  rtd    DIR    8,3               2 /
bash    4398 root  txt    REG    8,3         7077890 /bin/bash
bash    4398 root  mem    REG    8,3         1572903 /lib64/libresolv-2.12.so
bash    4398 root  mem    REG    8,3         1572891 /lib64/libnss_dns-2.12.so
bash    4398 root  mem    REG    8,3         1709499 /usr/lib/locale/locale-archive
bash    4398 root  mem    REG    8,3         1572893 /lib64/libnss_files-2.12.so
bash    4398 root  mem    REG    8,3         1572877 /lib64/libc-2.12.so
bash    4398 root  mem    REG    8,3         1572883 /lib64/libdl-2.12.so
bash    4398 root  mem    REG    8,3         1572920 /lib64/libtinfo.so.5.7
bash    4398 root  mem    REG    8,3         1572867 /lib64/ld-2.12.so
bash    4398 root  mem    REG    8,3         1968395 /usr/lib64/gconv/gconv-modules.cache
bash    4398 root    "0u   CHR  136,2    0t0        5 /dev/pts/2"
bash    4398 root    1u   CHR  136,2    0t0        5 /dev/pts/2
bash    4513 root    "0r  FIFO    0,8    0t0    39968 pipe"
bash    4398 root    2u   CHR  136,2    0t0        5 /dev/pts/2
bash    4398 root    "6r   REG    8,3    0t0 10227564 /root/ooxx.txt"
bash    4398 root    "8u  IPv4  39172    0t0      TCP node01:54723->104.193.88.123:http (CLOSE_WAIT)""
bash    4398 root  255u   CHR  136,2    0t0        5 /dev/pts/2

read a <& 6
通过读取6号文件描述符，查看0t4的offset变化

一切皆文件：这里主要展示 socket/pipeline
另外，fd，文件描述符代表打开的文件，有inode号和seek偏移指针的概念

```

```
-Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider
 strace -ff -o out java -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.PollSelectorProvider -cp /root/netty-all-
4.1.48.Final.jar:.  NettyIO
```


```
vm.dirty_background_ratio = 0
vm.dirty_background_bytes = 1048576
vm.dirty_ratio = 0
vm.dirty_bytes = 1048576
vm.dirty_writeback_centisecs = 5000
vm.dirty_expire_centisecs = 30000
```

```
cp  pcstat  /bin
```

```
net.ipv4.tcp_timestamps = 1
net.ipv4.tcp_window_scaling = 1
net.ipv4.tcp_sack = 1
net.ipv4.tcp_retrans_collapse = 1
net.ipv4.tcp_syn_retries = 5
net.ipv4.tcp_synack_retries = 5
net.ipv4.tcp_max_orphans = 262144
net.ipv4.tcp_max_tw_buckets = 262144
net.ipv4.tcp_keepalive_time = 1
net.ipv4.tcp_keepalive_probes = 9
net.ipv4.tcp_keepalive_intvl = 75
net.ipv4.tcp_retries1 = 3
net.ipv4.tcp_retries2 = 15
net.ipv4.tcp_fin_timeout = 60
net.ipv4.tcp_syncookies = 1
net.ipv4.tcp_tw_recycle = 0
net.ipv4.tcp_abort_on_overflow = 0
net.ipv4.tcp_stdurg = 0
net.ipv4.tcp_rfc1337 = 0
net.ipv4.tcp_max_syn_backlog = 2048
net.ipv4.tcp_orphan_retries = 0
net.ipv4.tcp_fack = 1
net.ipv4.tcp_reordering = 3
net.ipv4.tcp_ecn = 2
net.ipv4.tcp_dsack = 1
net.ipv4.tcp_mem = 364704	486272	729408
net.ipv4.tcp_wmem = 4096	16384	4194304
net.ipv4.tcp_rmem = 4096	87380	4194304
net.ipv4.tcp_app_win = 31
net.ipv4.tcp_adv_win_scale = 2
net.ipv4.tcp_tw_reuse = 0
net.ipv4.tcp_frto = 2
net.ipv4.tcp_frto_response = 0
net.ipv4.tcp_low_latency = 0
net.ipv4.tcp_no_metrics_save = 0
net.ipv4.tcp_moderate_rcvbuf = 1
net.ipv4.tcp_tso_win_divisor = 3
net.ipv4.tcp_congestion_control = cubic
net.ipv4.tcp_abc = 0
net.ipv4.tcp_mtu_probing = 0
net.ipv4.tcp_base_mss = 512
net.ipv4.tcp_workaround_signed_windows = 0
net.ipv4.tcp_challenge_ack_limit = 100
net.ipv4.tcp_limit_output_bytes = 131072
net.ipv4.tcp_dma_copybreak = 4096
net.ipv4.tcp_slow_start_after_idle = 1
net.ipv4.tcp_available_congestion_control = cubic reno
net.ipv4.tcp_allowed_congestion_control = cubic reno
net.ipv4.tcp_max_ssthresh = 0
net.ipv4.tcp_thin_linear_timeouts = 0
net.ipv4.tcp_thin_dupack = 0
net.ipv4.tcp_min_tso_segs = 2



//server socket listen property:
private static final int RECEIVE_BUFFER = 10;
private static final int SO_TIMEOUT = 0;
private static final boolean REUSE_ADDR = false;
private static final int BACK_LOG = 5;

//client socket listen property on server endpoint:
private static final boolean CLI_KEEPALIVE = false;
private static final boolean CLI_OOB = false;
private static final int CLI_REC_BUF = 20;
private static final boolean CLI_REUSE_ADDR = false;
private static final int CLI_SEND_BUF = 20;
private static final boolean CLI_LINGER = false;
private static final int CLI_LINGER_N = -1;
private static final int CLI_TIMEOUT = 5000;
private static final boolean CLI_NO_DELAY = true;




```












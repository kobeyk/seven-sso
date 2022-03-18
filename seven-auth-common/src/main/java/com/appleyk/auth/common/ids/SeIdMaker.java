package com.appleyk.auth.common.ids;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * <p>雪花算法，高效产出分布式ID</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-13:13
 */
public class SeIdMaker {

    private static final long START_STAMP = 1288834974657L;
    private static final long SEQUENCE_BIT = 6L;
    private static final long MACHINE_BIT = 3L;
    private static final long DATA_CENTER_BIT = 3L;
    private static final long MAX_MACHINE_NUM = 7L;
    private static final long MAX_DATA_CENTER_NUM = 7L;
    private static final long MAX_SEQUENCE = 63L;
    private static final long MACHINE_LEFT = 6L;
    private static final long DATA_CENTER_LEFT = 9L;
    private static final long TIMESTAMP_LEFT = 12L;
    private static long lastTimestamp = -1L;
    private long sequence = 0L;

    private long machineId = 7;
    private long dataCenterId = 7;

    public SeIdMaker() {
        this.dataCenterId = getDataCenterId(7L);
        this.machineId = getMaxWorkerId(this.dataCenterId, 7L);
    }

    public long getMachineId() {
        return this.machineId;
    }

    public SeIdMaker(long machineId, long dataCenterId) {
        if (machineId <= 7L && machineId >= 0L) {
            if (dataCenterId <= 7L && dataCenterId >= 0L) {
                this.machineId = machineId;
                this.dataCenterId = dataCenterId;
            } else {
                throw new IllegalArgumentException(String.format("dataCenter Id can't be greater than %d or less than 0", 7L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 7L));
        }
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        } else {
            if (lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 63L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }
            lastTimestamp = timestamp;
            long nextId = timestamp - 1288834974657L << 12 | this.dataCenterId << 9 | this.machineId << 6 | this.sequence;
            return nextId;
        }
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    protected static long getMaxWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            mpid.append(name.split("@")[0]);
        }
        return (long)(mpid.toString().hashCode() & '\uffff') % (maxWorkerId + 1L);
    }

    protected static long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = (255L & (long)mac[mac.length - 1] | 65280L & (long)mac[mac.length - 2] << 8) >> 6;
                id %= maxDataCenterId + 1L;
            }
        } catch (Exception var7) {
            System.out.println(" getDataCenterId: " + var7.getMessage());
        }
        return id;
    }

}

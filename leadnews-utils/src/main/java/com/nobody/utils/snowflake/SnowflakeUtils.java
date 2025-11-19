package com.nobody.utils.snowflake;


import java.util.concurrent.atomic.AtomicLong;

/**
 * 雪花算法工具类（由Spring管理，通过配置属性绑定数据中心ID和机器ID）
 */
public class SnowflakeUtils {

    private SnowflakeProperties snowflakeProperties;

    public SnowflakeUtils(SnowflakeProperties snowflakeProperties) {
        this.snowflakeProperties = snowflakeProperties;
        this.dataCenterId = snowflakeProperties.getDataCenterId();
        this.machineId = snowflakeProperties.getMachineId();
    }

    private long dataCenterId;  // 数据中心ID
    private long machineId;     // 机器ID
    // 起始时间戳（可自定义，示例：2021-01-01 00:00:00）
    private static final long START_TIMESTAMP = 1609459200000L;

    // 位数分配（总和≤63，因最高位为符号位）
    private static final long DATA_CENTER_BITS = 5L;  // 数据中心ID范围：0-31
    private static final long MACHINE_BITS = 5L;       // 机器ID范围：0-31
    private static final long SEQUENCE_BITS = 12L;     // 序列号范围：0-4095

    // 最大取值（位运算计算）
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_BITS);
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_BITS);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    // 移位偏移量
    private static final long MACHINE_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_SHIFT = SEQUENCE_BITS + MACHINE_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_BITS + DATA_CENTER_BITS;

    // 实例变量（由配置注入，原子类保证线程安全）
    private final AtomicLong sequence = new AtomicLong(0L);  // 序列号
    private final AtomicLong lastTimestamp = new AtomicLong(-1L);  // 上次生成ID的时间戳

    /**
     * 构造器：由Spring注入数据中心ID和机器ID
     */
    public SnowflakeUtils() {
        // 校验参数合法性
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER_ID) {
            throw new IllegalArgumentException("数据中心ID超出范围（0~" + MAX_DATA_CENTER_ID + "）");
        }
        if (machineId < 0 || machineId > MAX_MACHINE_ID) {
            throw new IllegalArgumentException("机器ID超出范围（0~" + MAX_MACHINE_ID + "）");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }



    /**
     * 生成全局唯一ID
     */
    public long generateId() {
        long currentTimestamp = System.currentTimeMillis();

        // 处理时钟回拨
        if (currentTimestamp < lastTimestamp.get()) {
            long offset = lastTimestamp.get() - currentTimestamp;
            if (offset <= 5) {
                try {
                    Thread.sleep(offset);
                    currentTimestamp = System.currentTimeMillis();
                } catch (InterruptedException e) {
                    throw new RuntimeException("时钟回拨处理中断", e);
                }
            } else {
                throw new RuntimeException("时钟回拨过大（偏移量：" + offset + "ms），拒绝生成ID");
            }
        }

        // 同一毫秒内序列号自增
        if (currentTimestamp == lastTimestamp.get()) {
            long nextSeq = sequence.incrementAndGet();
            if (nextSeq > MAX_SEQUENCE) {
                currentTimestamp = waitForNextMillis(currentTimestamp);
                sequence.set(0L);
            }
        } else {
            sequence.set(0L);
        }

        lastTimestamp.set(currentTimestamp);

        // 拼接ID：时间戳差 + 数据中心ID + 机器ID + 序列号
        return ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT)
                | (dataCenterId << DATA_CENTER_SHIFT)
                | (machineId << MACHINE_SHIFT)
                | sequence.get();
    }

    /**
     * 等待至下一毫秒
     */
    private long waitForNextMillis(long currentTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= currentTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
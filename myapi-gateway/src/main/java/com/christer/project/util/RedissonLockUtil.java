package com.christer.project.util;

import com.christer.myapicommon.common.ResultCode;
import com.christer.myapicommon.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-20 16:52
 * Description:
 */
@Component
@Slf4j
public class RedissonLockUtil {


    private final RedissonClient redissonClient;

    public RedissonLockUtil(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * redisson分布式锁
     *
     * @param lockName     锁名称
     * @param runnable     可运行
     * @param errorMessage 错误消息
     */
    public void redissonDistributedLocks(String lockName, Runnable runnable, String errorMessage) {
        redissonDistributedLocks(lockName, runnable, ResultCode.FAILED, errorMessage);
    }

    /**
     * redisson分布式锁
     *
     * @param lockName     锁名称
     * @param runnable     可运行
     * @param errorCode    错误代码
     * @param errorMessage 错误消息
     */
    public void redissonDistributedLocks(String lockName, Runnable runnable, ResultCode errorCode, String errorMessage) {

        RLock rLock = redissonClient.getLock(lockName);

        try {
            // waitTime  = 0,若无法获取锁，立即返回异常
            // leaseTime = -1 不设置释放时间，则启用看门狗机制
            if (rLock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                runnable.run();
            } else {
                throw new BusinessException(errorCode.getCode(), errorMessage);
            }
        } catch (InterruptedException e) {
            log.error("获取锁时发生错误了...:{0}", e);
            throw new BusinessException(ResultCode.FAILED, e.getMessage());
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                log.info("Finally，释放锁成功，lockName:{},unLockId:{} ", lockName, Thread.currentThread().getId());
                rLock.unlock();
            }
        }
    }
}

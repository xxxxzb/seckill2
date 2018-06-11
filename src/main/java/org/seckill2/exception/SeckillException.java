package org.seckill2.exception;

/**
 * (运行期)秒杀异常
 * mysql只支持运行期异常回滚
 */
public class SeckillException extends RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}

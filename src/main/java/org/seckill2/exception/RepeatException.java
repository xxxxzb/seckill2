package org.seckill2.exception;

/**
 * (运行期)秒杀异常之 重复秒杀
 */
public class RepeatException extends SeckillException{
    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }
}

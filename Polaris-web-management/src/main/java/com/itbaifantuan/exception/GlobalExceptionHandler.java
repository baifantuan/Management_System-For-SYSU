package com.itbaifantuan.exception;

import com.itbaifantuan.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.info("出现异常:" + e);
        return Result.error("服务端出现问题，请联系管理员");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.warn("数据库唯一约束冲突:" + e.getMessage());
        String errorMsg = e.getMessage();
        int i = errorMsg.indexOf("Duplicate entry");
        String msg = errorMsg.substring(i);
        String[] str = msg.split(" ");
        return Result.error(str[2] + "已存在");
    }

    @ExceptionHandler(CustomBusinessException.class)
    public Result handleCustomBusinessException(CustomBusinessException e){
        log.warn("发生业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }
}

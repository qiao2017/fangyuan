package com.fangyuan.exception;


import com.fangyuan.result.CodeMsg;
import com.fangyuan.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<CodeMsg> handle(Exception e){
        return Result.failure(CodeMsg.SERVER_ERROR);
    }
}

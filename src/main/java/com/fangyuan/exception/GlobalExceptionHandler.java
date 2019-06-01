package com.fangyuan.exception;


import com.fangyuan.result.CodeMsg;
import com.fangyuan.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<CodeMsg> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("错误1");
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.failure(CodeMsg.BIND_ERROR.fillArgs(message));
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result<CodeMsg> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("错误2");
        return Result.failure(CodeMsg.PARAM_ERROR);
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<CodeMsg> handleConstraintViolation(ConstraintViolationException e){
        log.error("错误3");
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return Result.failure(CodeMsg.BIND_ERROR.fillArgs(message));
    }
    @ExceptionHandler(value = Exception.class)
    public Result<CodeMsg> handle(Exception e){
        log.error("错误4");
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.failure(CodeMsg.SERVER_ERROR);
    }
    @ExceptionHandler(RegisterException.class)
    @ResponseBody
    public Result<CodeMsg> RegisterExceptionHandler(RegisterException e) {
        return Result.failure(CodeMsg.REGISTER_ERROR.fillArgs(e.getMessage()));
    }
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<CodeMsg> BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Result.failure(CodeMsg.BIND_ERROR.fillArgs(message));
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result<CodeMsg> BindExceptionHandler(MissingServletRequestParameterException e) {
        return Result.failure(CodeMsg.PARAM_MISSING_ERROR.fillArgs(e.getParameterName()));
    }

    @ExceptionHandler(RoomException.class)
    @ResponseBody
    public Result<CodeMsg> RoomExceptionHandler(RoomException e) {
        return Result.failure(CodeMsg.ROOM_ERROR.fillArgs(e.getMessage()));
    }
}

package io.github.toquery.framework.webmvc.controller.advice;

import io.github.toquery.framework.core.exception.AppException;
import io.github.toquery.framework.webmvc.domain.ResponseResult;
import io.github.toquery.framework.webmvc.domain.ResponseBodyBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
 * @author toquery
 * @version 1
 */
@Slf4j
// @ControllerAdvice
@RestControllerAdvice
public class AppExceptionAdvice {

    public AppExceptionAdvice() {
        log.info("初始化 App 异常捕获");
    }


    @ResponseStatus(code=HttpStatus.NOT_FOUND)
    @ExceptionHandler(value= NoHandlerFoundException.class)
    public ResponseEntity<ResponseResult> handleExceptionNoHandlerFoundException(NoHandlerFoundException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseResult.builder().message("请求地址错误！").build());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult> handleException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBodyBuilder().fail().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(exception.getMessage()).build());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseResult> handleAppException(AppException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBodyBuilder().fail().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(exception.getMessage()).build());
    }

    /*
    @ResponseBody
    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<ResponseParam> handleRollbackException(RollbackException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseParam.builder().build().message(e.getMessage()));
    }
    */


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBodyBuilder().fail().code(HttpStatus.BAD_REQUEST.value()).message(fieldError.getField() + fieldError.getDefaultMessage()).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseResult> handleConstraintViolationException(ConstraintViolationException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBodyBuilder().fail().code(HttpStatus.BAD_REQUEST.value()).message(exception.getConstraintViolations().iterator().next().getMessage()).build());
    }
}

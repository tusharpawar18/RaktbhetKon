package com.sparkCoder.raktbhet.globalexception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        //return  ResponseEntity.ok("kuch accha nahi chal raha hai");
        //return ResponseEntity.ok(ex.getMessage());
        Map<String ,String>errorMap= ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(fieldError ->fieldError.getField(), fieldError->fieldError.getDefaultMessage(),(prevValue, newValue)->newValue));
        return ResponseEntity.ok(errorMap);
    }

    /*public ResponseEntity<Map<String, List<String>>> MethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {

        Map<String ,List<String>> errorMap= ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(fieldError ->fieldError.getField(), fieldError->fieldError.getDefaultMessage(),(prevValue, newValue)->newValue));
        return ResponseEntity.ok(errorMap);
    }*/
}

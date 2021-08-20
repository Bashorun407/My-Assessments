package com.search.booksearch.RestResponse;

import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@Data
@ControllerAdvice
public class Restponsepojo<T> {

    String message;
    Boolean success = true;
    T data;
    List<T> list;
    int status = 200;
}

package com.search.booksearch.RestResponse;

import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@Data
public class Restponsepojo<T> {

    String message;
    Boolean success = true;
    T data;
    int status = 200;
}

package com.lambdaschool.school.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UrlNotFoundException extends RuntimeException
{
    public UrlNotFoundException(String message)
    {
        // update message
        super(message);
    }

    public UrlNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

package com.project.exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import java.io.Serial;
public class HttpException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public HttpException(HttpStatusCode httpStatusCode, HttpHeaders httpHeaders) {
        super(String.format("Error: statusCode - %s, headers - %s", httpStatusCode, httpHeaders));
    }
    public HttpException(String errorMessage) {
        super(errorMessage);
    }
    public HttpException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}

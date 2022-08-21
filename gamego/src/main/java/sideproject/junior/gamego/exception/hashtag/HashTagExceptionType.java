package sideproject.junior.gamego.exception.hashtag;

import org.springframework.http.HttpStatus;
import sideproject.junior.gamego.exception.BaseExceptionType;

public enum HashTagExceptionType implements BaseExceptionType {
    NAME_REQUEST_NULL(901,HttpStatus.BAD_REQUEST,"NAME 값이 없습니다"),;


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    HashTagExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}

package sideproject.junior.gamego.exception.gamer;

import org.springframework.http.HttpStatus;
import sideproject.junior.gamego.exception.BaseExceptionType;

public enum GamerExceptionType implements BaseExceptionType {
    ALEARY_REGISTATION_MEMBER(701,HttpStatus.CONFLICT,"이미 등록한 유저입니다");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    GamerExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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

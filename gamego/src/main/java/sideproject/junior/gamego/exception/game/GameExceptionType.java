package sideproject.junior.gamego.exception.game;

import org.springframework.http.HttpStatus;
import sideproject.junior.gamego.exception.BaseExceptionType;

public enum GameExceptionType implements BaseExceptionType {
    NAME_OR_CONTENT_NULL(801,HttpStatus.BAD_REQUEST,"값이 없습니다"),
    CHANGE_API_REQUEST_NULL(802,HttpStatus.BAD_REQUEST,"유저의 id값 또는 이름,내용이 비어있습니다"),
    DELETE_API_REQUEST_NULL(803,HttpStatus.BAD_REQUEST,"id가 존재하지 않습니다");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    GameExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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

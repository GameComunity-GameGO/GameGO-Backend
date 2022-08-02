package sideproject.junior.gamego.exception.member;

import org.springframework.http.HttpStatus;
import sideproject.junior.gamego.exception.BaseExceptionType;

public enum MemberExceptionType implements BaseExceptionType {
    ALREADY_EXIST_USERNAME(600, HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    WRONG_PASSWORD(601,HttpStatus.BAD_REQUEST, "비밀번호양식이 잘못되었습니다."),
    NOT_FOUND_MEMBER(602, HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    NOT_LOGIN_MEMBER(603,HttpStatus.FORBIDDEN,"로그인 후 이용하세요"),
    NULL_OF_USERNAME_OR_PASSWORD(604,HttpStatus.BAD_REQUEST,"아이디 또는 비밀번호가 입력되지 않았습니다");



    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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

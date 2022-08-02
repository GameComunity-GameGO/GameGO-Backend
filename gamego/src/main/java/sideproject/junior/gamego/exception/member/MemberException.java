package sideproject.junior.gamego.exception.member;
import sideproject.junior.gamego.exception.BaseException;
import sideproject.junior.gamego.exception.BaseExceptionType;

public class MemberException extends BaseException {
    private BaseExceptionType exceptionType;


    public MemberException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}

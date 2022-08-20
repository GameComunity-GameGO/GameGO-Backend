package sideproject.junior.gamego.exception.hashtag;
import sideproject.junior.gamego.exception.BaseException;
import sideproject.junior.gamego.exception.BaseExceptionType;

public class HashTagException extends BaseException {
    private BaseExceptionType exceptionType;


    public HashTagException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}

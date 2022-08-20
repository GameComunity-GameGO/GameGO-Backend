package sideproject.junior.gamego.exception.gamer;
import sideproject.junior.gamego.exception.BaseException;
import sideproject.junior.gamego.exception.BaseExceptionType;

public class GamerException extends BaseException {
    private BaseExceptionType exceptionType;


    public GamerException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}

package sideproject.junior.gamego.exception.game;
import sideproject.junior.gamego.exception.BaseException;
import sideproject.junior.gamego.exception.BaseExceptionType;

public class GameException extends BaseException {
    private BaseExceptionType exceptionType;


    public GameException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}

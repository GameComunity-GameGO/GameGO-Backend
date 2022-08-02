package sideproject.junior.gamego.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class ExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseEx(BaseException exception){
        log.error("BaseException errorMessage(): {}",exception.getExceptionType().getErrorMessage());
        log.error("BaseException errorCode(): {}",exception.getExceptionType().getErrorCode());

        return new ResponseEntity(new ExceptionDto(exception.getExceptionType().getErrorCode()),exception.getExceptionType().getHttpStatus());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ExceptionDto {
        private Integer errorCode;
    }
}

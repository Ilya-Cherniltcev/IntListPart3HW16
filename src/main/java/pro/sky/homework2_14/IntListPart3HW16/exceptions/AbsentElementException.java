package pro.sky.homework2_14.IntListPart3HW16.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// =====   ошибка 400 Bad Request =======
@ResponseStatus(HttpStatus.BAD_REQUEST)

public class AbsentElementException extends RuntimeException {
    public AbsentElementException(String alert) {
    }
}

package ucmo.workoutapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CoachNotFoundException extends RuntimeException {
    public CoachNotFoundException(String message) {
        super(message);
    }
}
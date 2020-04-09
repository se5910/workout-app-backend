package ucmo.workoutapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.web.bind.annotation.ResponseStatus;
import ucmo.workoutapp.entities.User;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String message) {super(message);}
}

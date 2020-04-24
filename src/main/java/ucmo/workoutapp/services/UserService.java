package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.UsernameAlreadyExistsException;
import ucmo.workoutapp.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            // Username has to be unique
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // Don't persist or show confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername()+ "' already exists");
        }
    }

    public User isCoach(String username) {
        try {
            User coach = userRepository.findByUsername(username);

            if (!coach.isCoach()) {
                throw new CoachNotFoundException("This user is not a coach");
            }
            User userObject = new User();
            userObject.setUsername(coach.getUsername());
            userObject.setCoach(coach.isCoach());
            userObject.setFullName(coach.getFullName());
            userObject.setId(coach.getId());


            return userObject;
        } catch(Exception ex) {
            throw new CoachNotFoundException("Coach not found");
        }
    }


}


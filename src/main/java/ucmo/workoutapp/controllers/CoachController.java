package ucmo.workoutapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.services.UserService;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/coach")
public class CoachController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> coachAuthentication(Principal principal) {
        User coach = userService.isCoach(principal.getName());
        return new ResponseEntity<>(coach, HttpStatus.OK);
    }
}

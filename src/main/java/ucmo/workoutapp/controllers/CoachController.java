package ucmo.workoutapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.Questionnaire;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.services.ClientService;
import ucmo.workoutapp.services.QuestionnaireService;
import ucmo.workoutapp.services.UserService;

import java.security.Principal;
import java.util.Iterator;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/coach")
public class CoachController {

    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;

    @Autowired
    QuestionnaireService questionnaireService;

    @GetMapping("")
    public ResponseEntity<?> coachAuthentication(Principal principal) {
        User coach = userService.isCoach(principal.getName());
        return new ResponseEntity<>(coach, HttpStatus.OK);
    }

    @GetMapping("/questionnaire")
    public Iterable<Questionnaire> getAllQuestionnairesOfCoach(Principal principal){
       return questionnaireService.getAllQuestionnairesOfCoach(principal.getName());

    }

    @GetMapping("/clients")
    public Iterable<?> getAllClientsByCoach(Principal principal) {
        return clientService.getAllClients();
    }
}

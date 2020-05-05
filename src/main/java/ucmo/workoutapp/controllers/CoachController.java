package ucmo.workoutapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.services.ClientService;
import ucmo.workoutapp.services.UserService;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/coach")
public class CoachController {

    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;


    @GetMapping("")
    public ResponseEntity<?> coachAuthentication(Principal principal) {
        User coach = userService.isCoach(principal.getName());
        return new ResponseEntity<>(coach, HttpStatus.OK);
    }

    @PostMapping("/approve/client/{clientId}")
    public ResponseEntity<?> approveClient(@PathVariable Long clientId, Principal principal) {
        Client approvedClient = clientService.approveClient(clientId, principal.getName());

        return new ResponseEntity<>(approvedClient, HttpStatus.OK);
    }

    @GetMapping("/clients")
    public Iterable<Client> getAllClientsByCoach(Principal principal) {
        return clientService.getAllClientsByCoach(principal.getName());
    }
}

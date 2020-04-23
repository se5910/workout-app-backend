package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.ClientService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/client")
    public ResponseEntity<?> createOrUpdateClient(@Valid @RequestBody Client client, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Client newClient = clientService.SaveOrUpdateClient(client, principal.getName());
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @GetMapping("/client/me")
    public ResponseEntity<?> getCurrentClient(Principal principal) {
        Client currentClient = clientService.getClientByUser(principal.getName());
        System.out.println(principal.getName());

        return new ResponseEntity<>(currentClient, HttpStatus.OK);
    }


}

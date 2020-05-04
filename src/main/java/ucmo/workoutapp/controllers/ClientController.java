package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.Questionnaire;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.ClientService;
import ucmo.workoutapp.services.QuestionnaireService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // @route   POST api/client
    // @desc    Create a client associated to user
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createOrUpdateClient(@Valid @RequestBody Client client, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Client newClient = clientService.SaveOrUpdateClient(client, principal.getName());
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    // @route   POST api/client/me
    // @desc    Return client info for current user
    // @access  Private
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentClient(Principal principal) {
        Client currentClient = clientService.getClientByUser(principal.getName());

        return new ResponseEntity<>(currentClient, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public Client getClientById(@PathVariable Long clientId, Principal principal) {
        return clientService.getClientById(clientId, principal.getName());
    }


}

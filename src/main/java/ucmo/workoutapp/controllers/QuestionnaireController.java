package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Questionnaire;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.QuestionnaireService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/users/{userId}/questionnaire")
public class QuestionnaireController {
    @Autowired
    QuestionnaireService questionnaireService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createQuestionnaireForUser(@Valid @RequestBody Questionnaire questionnaire, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Questionnaire newQuestionnaire = questionnaireService.createQuestionnaireForUser(questionnaire, principal.getName());

        return new ResponseEntity<>(newQuestionnaire, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity getQuestionnaireOfUser(@PathVariable Long userId, Principal principal){
        Questionnaire questionnaire = questionnaireService.getQuestionnaireOfUser(userId, principal.getName());

        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
    }
}

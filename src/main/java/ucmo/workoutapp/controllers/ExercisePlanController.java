package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.ExercisePlanService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/client/{clientId}/exercisePlan")
public class ExercisePlanController {
    @Autowired
    private ExercisePlanService exercisePlanService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // @route   POST api/exercisePlan
    // @desc    Register a user
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createNewExercisePlan(@Valid @RequestBody ExercisePlan exercisePlan, @PathVariable Long clientId, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exercisePlanService.SaveOrUpdateExercisePlan(clientId, exercisePlan, principal.getName());
        return new ResponseEntity<>(exercisePlan, HttpStatus.CREATED);

    }

    // @route   GET api/exercisePlan/all
    // @desc    Get all exercise plans of user
    // @access  Private
    @GetMapping("")
    public Iterable<ExercisePlan> getAllExercisePlans(Principal principal) {
        return exercisePlanService.findAllExercisePlans(principal.getName());
    }

    // @route   GET api/exercisePlan/:planId
    // @desc    Get exercise plan by id
    // @access  Private
    @GetMapping("/{planId}")
    public ExercisePlan getExercisePlanById(@PathVariable Long clientId, @PathVariable Long planId, Principal principal){
        return exercisePlanService.getExercisePlanById(clientId, planId, principal.getName());

    }

    // @route   DELETE api/exercisePlan/:planId
    // @desc    Delete exercise plan by id
    // @access  Private
    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deleteExercisePlanById(@PathVariable Long planId, Principal principal){
        exercisePlanService.deleteByExercisePlanId(planId, principal.getName());

        return new ResponseEntity<>("Plan with ID: '" + planId + "' was deleted.", HttpStatus.OK);
    }

    // @route   GET api/exercisePlan/client/:cliendId
    // @desc    Get all exercise plans of client
    // @access  Private
    @GetMapping("/client/{clientId}")
    public Iterable<ExercisePlan> getAllExercisePlansByClient(@PathVariable Long clientId, Principal coach) {
        return exercisePlanService.findAllExercisePlansOfClient(clientId, coach.getName());
    }
}

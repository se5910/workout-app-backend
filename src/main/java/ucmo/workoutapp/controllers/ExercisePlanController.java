package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.SpringCacheAnnotationParser;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.DayService;
import ucmo.workoutapp.services.ExercisePlanService;
import ucmo.workoutapp.services.ExerciseSlotService;
import ucmo.workoutapp.services.WeekService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercisePlan")
public class ExercisePlanController {
    @Autowired
    private ExercisePlanService exercisePlanService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // @route   POST api/exercise
    // @desc    Register a user
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createNewExercisePlan(@Valid @RequestBody ExercisePlan exercisePlan, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exercisePlanService.SaveOrUpdateExercisePlan(exercisePlan, principal.getName());
        return new ResponseEntity<>(exercisePlan, HttpStatus.CREATED);

    }

    // @route   GET api/exercise/all
    // @desc    Get all exercise plans of user
    // @access  Private
    @GetMapping("")
    public Iterable<ExercisePlan> getAllExercisePlans(Principal principal) {
        return exercisePlanService.findAllExercisePlans(principal.getName());
    }

    @GetMapping("/{planId}")
    public ResponseEntity<?> getExercisePlanById(@PathVariable Long planId, Principal principal){
        ExercisePlan exercisePlan = exercisePlanService.findExercisePlanById(planId, principal.getName());

        return new ResponseEntity<>(planId, HttpStatus.OK);

    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deleteExercisePlanById(@PathVariable Long planId, Principal principal){
        exercisePlanService.deleteByExercisePlanId(planId, principal.getName());

        return new ResponseEntity<>("Plan with ID: '" + planId + "' was deleted.", HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public Iterable<ExercisePlan> getAllExercisePlansByClient(@PathVariable Long clientId, Principal coach) {
        return exercisePlanService.findAllExercisePlansOfClient(clientId, coach.getName());
    }
}

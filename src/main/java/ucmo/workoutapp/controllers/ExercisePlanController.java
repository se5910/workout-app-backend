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
@RequestMapping("/api/exercise")
public class ExercisePlanController {
    @Autowired
    private ExercisePlanService exercisePlanService;

    @Autowired
    private DayService dayService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseSlotService exerciseSlotService;

    @Autowired
    private WeekService weekService;

    @PostMapping("")
    public ResponseEntity<?> createNewExercisePlan(@Valid @RequestBody ExercisePlan exercisePlan, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exercisePlanService.SaveOrUpdateExercisePlan(exercisePlan, principal.getName());
        return new ResponseEntity<>(exercisePlan, HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public  Iterable<ExercisePlan> getAllExercisePlans(Principal principal) {

        return exercisePlanService.findAllExercisePlans(principal.getName());
    }

}

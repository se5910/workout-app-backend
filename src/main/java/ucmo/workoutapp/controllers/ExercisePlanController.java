package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.services.ExercisePlanService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercise")
public class ExercisePlanController {
    @Autowired
    private ExercisePlanService exercisePlanService;

    @PostMapping("")
    public ResponseEntity<?> createNewExercisePlan(@Valid @RequestBody ExercisePlan exercisePlan, BindingResult result, Principal principal) {
        exercisePlanService.SaveOrUpdateExercisePlan(exercisePlan, principal.getName());
        return new ResponseEntity<>(exercisePlan, HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public  Iterable<ExercisePlan> getAllExercisePlans(Principal principal) {
        return exercisePlanService.findAllExercisePlans(principal.getName());
    }
}

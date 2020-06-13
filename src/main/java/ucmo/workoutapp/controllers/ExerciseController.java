package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.ExerciseService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("")
    public ResponseEntity<?> createExercise(@Valid @RequestBody Exercise exercise, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exerciseService.createExercise(exercise, principal.getName());

        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<?> getExerciseById(@PathVariable Long exerciseId, Principal principal){
        Exercise exercise = exerciseService.getExerciseById(exerciseId, principal.getName());

        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<?> deleteExerciseById(@PathVariable Long exerciseId, Principal principal){
        exerciseService.deleteExerciseById(exerciseId, principal.getName());

        return new ResponseEntity<>("Plan with ID: '" + exerciseId + "' was deleted.", HttpStatus.OK);

    }
}

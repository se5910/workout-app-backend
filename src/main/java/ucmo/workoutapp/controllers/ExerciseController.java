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

@RestController
@RequestMapping("/api/exercisePlan/{planId}/{dayId}/{weekId}/{exerciseSlotId}")
public class ExerciseController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("/exercise")
    public ResponseEntity<?> createExerciseForExerciseSlot(@Valid @RequestBody Exercise exercise, BindingResult result, @PathVariable Long exerciseSlotId, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exerciseService.createExerciseForExerciseSlot(exercise, exerciseSlotId, principal.getName());

        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<?> getExerciseById(@PathVariable Long exerciseSlotId, @PathVariable Long exerciseId, Principal principal){
        Exercise exercise = exerciseService.getExerciseById(exerciseSlotId, exerciseId, principal.getName());

        return new ResponseEntity<>(exercise, HttpStatus.OK);

    }
}

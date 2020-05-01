package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercisePlan/{planId}/day/{daId}/week/{weekId}/exerciseSlot")
public class ExerciseSlotController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseSlotService exerciseSlotService;

    @Autowired
    private ExerciseService exerciseService;

    // @route   POST /api/exercise/{planId}/{dayId}/{weekId}
    // @desc    Create exercise Slot for day
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createExerciseSlotForDay(@Valid @RequestBody ExerciseSlot exerciseSlot, BindingResult result, @PathVariable Long weekId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exerciseSlotService.createExerciseSlotForDay(exerciseSlot, weekId, principal.getName());

        return new ResponseEntity<>(exerciseSlot, HttpStatus.CREATED);
    }

    // @route   POST api/exercise/{planId}/{dayId}/{weekId}/{exerciseSlotId}
    // @desc    Get all exercise slots from day
    // @access  Private
    @GetMapping("/{exerciseSlotId}")
    public ResponseEntity<?> getExerciseSlotById(@PathVariable Long exerciseSlotId, Principal principal){
       ExerciseSlot exerciseSlot = exerciseSlotService.getExerciseSlotById(exerciseSlotId, principal.getName());

        return new ResponseEntity<>(exerciseSlot, HttpStatus.OK);
    }

    @DeleteMapping("/{exerciseSlotId}")
    public ResponseEntity<?> deleteExercisesSlotById(@PathVariable Long exerciseSlotId, Principal principal){
        exerciseSlotService.deleteExerciseSlotById(exerciseSlotId, principal.getName());

        return new ResponseEntity<>("Exercise slot with ID: '" + exerciseSlotId + "' was deleted.", HttpStatus.OK);
    }

    @PutMapping("/{exerciseSlotId}/exercise/{exerciseId}")
    public ResponseEntity<?> createExerciseForExerciseSlot(@PathVariable Long exerciseSlotId, @PathVariable Long exerciseId, Principal principal){
        exerciseSlotService.createExerciseForExerciseSlot(exerciseSlotId, exerciseId, principal.getName());

        return new ResponseEntity<>("Exercise with ID: '" + exerciseId + "' was added to ExerciseSlot with ID: '" + exerciseSlotId + "'.", HttpStatus.CREATED);
    }

    @GetMapping("/{exerciseSlotId}/exercise/")
    public ResponseEntity<?> getExerciseFromExerciseSlot(@PathVariable Long exerciseSlotId, Principal principal){
        Exercise exercise = exerciseSlotService.getExerciseFromExerciseSlotById(exerciseSlotId, principal.getName());

        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }


    @DeleteMapping("/{exerciseSlotId}/exercise/")
    public ResponseEntity<?> deleteExerciseForExerciseSlot(@PathVariable Long exerciseSlotId, Principal principal){
        exerciseSlotService.deleteExerciseFromExerciseSlotById(exerciseSlotId, principal.getName());

        return new ResponseEntity<>("The exercise was removed from ExerciseSlot(ID): '" + exerciseSlotId + "'.", HttpStatus.OK);
    }
}

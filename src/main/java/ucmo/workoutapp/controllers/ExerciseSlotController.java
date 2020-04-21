package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Day;
import ucmo.workoutapp.entities.ExerciseSlot;
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
public class ExerciseSlotController {

    @Autowired
    private DayService dayService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseSlotService exerciseSlotService;


    @PostMapping("/{planId}/{dayId}/{weekId}/exerciseSlot")
    public ResponseEntity<?> createExerciseSlotForDay(@Valid @RequestBody ExerciseSlot exerciseSlot, BindingResult result, @PathVariable Long weekId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exerciseSlotService.createExerciseSlotForDay(exerciseSlot, weekId, principal.getName());

        return new ResponseEntity<>(exerciseSlot, HttpStatus.CREATED);
    }

    @GetMapping("/{planId}/{dayId}/{weekId}/{exerciseSlotId}")
    public ResponseEntity<?> getAllExerciseSlotsFromDay(@PathVariable Long weekId, @PathVariable Long exerciseSlotId, Principal principal){
       ExerciseSlot exerciseSlot = exerciseSlotService.getExerciseSlotById(weekId , exerciseSlotId, principal.getName());

        return new ResponseEntity<>(exerciseSlot, HttpStatus.OK);
    }
}

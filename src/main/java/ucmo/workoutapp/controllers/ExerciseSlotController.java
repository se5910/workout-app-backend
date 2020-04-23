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
@RequestMapping("/api/exercise/{planId}/{dayId}/{weekId}")
public class ExerciseSlotController {


    @Autowired
    private DayService dayService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseSlotService exerciseSlotService;


    // @route   POST /api/exercise/{planId}/{dayId}/{weekId}
    // @desc    Create exercise Slot for day
    // @access  Private
    @PostMapping("/exerciseSlot")
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
    public ResponseEntity<?> getAllExerciseSlotsFromDay(@PathVariable Long weekId, @PathVariable Long exerciseSlotId, Principal principal){
       ExerciseSlot exerciseSlot = exerciseSlotService.getExerciseSlotById(weekId , exerciseSlotId, principal.getName());

        return new ResponseEntity<>(exerciseSlot, HttpStatus.OK);
    }
}

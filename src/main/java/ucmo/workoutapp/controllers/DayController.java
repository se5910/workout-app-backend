package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Day;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.DayService;
import ucmo.workoutapp.services.ExercisePlanService;
import ucmo.workoutapp.services.ExerciseSlotService;
import ucmo.workoutapp.services.WeekService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercisePlan/{planId}")
public class DayController {
    @Autowired
    private DayService dayService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // @route   POST api/exercise/day/:planId
    // @desc    Create day on exercise plan
    // @access  Private
    @PostMapping("/day")
    public ResponseEntity<?> createDayForExercisePlan(@Valid @RequestBody Day day, BindingResult result, @PathVariable Long planId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        dayService.createDayForExercisePlan(day, planId, principal.getName());

        return new ResponseEntity<>(day, HttpStatus.CREATED);
    }

    // @route   GET api/exercise/day/:planId/:dayId
    // @desc    Get all days from planId
    // @access  Private
    @GetMapping("/{dayId}")
    public ResponseEntity<?> getAllDaysFromExercisePlan(@PathVariable Long planId, @PathVariable Long dayId, Principal principal){
        Day day = dayService.getDayById(planId, dayId, principal.getName());

        return new ResponseEntity<>(day, HttpStatus.OK);
    }


}

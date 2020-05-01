package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.DayService;
import ucmo.workoutapp.services.ExercisePlanService;
import ucmo.workoutapp.services.ExerciseSlotService;
import ucmo.workoutapp.services.WeekService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercisePlan/{planId}/day/{dayId}/week")
public class WeekController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private WeekService weekService;

    // @route   POST api/exercise/:planId/:dayId/week
    // @desc    Create week for day
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createWeekForDay(@Valid @RequestBody Week week, BindingResult result, @PathVariable Long dayId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        weekService.createWeekForDay(week, dayId, principal.getName());

        return new ResponseEntity<>(week, HttpStatus.CREATED);
    }

    // @route   GET api/exercise/:planId/:dayId/:weekId
    // @desc    Get all weeks from day
    // @access  Private
    @GetMapping("/{weekId}")
    public ResponseEntity<?> getWeekById(@PathVariable Long weekId, Principal principal){
        Week week = weekService.getWeekById(weekId, principal.getName());

        return new ResponseEntity<>(week, HttpStatus.OK);
    }

    @DeleteMapping("/{weekId}")
    public ResponseEntity<?> deleteWeekFromDay(@PathVariable Long weekId, Principal principal){
        weekService.deleteWeekById(weekId, principal.getName());

        return new ResponseEntity<>("Week with ID: '" + weekId + "' was deleted.", HttpStatus.OK);
    }

}

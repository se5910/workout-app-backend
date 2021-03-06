package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.ExerciseSet;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.ExerciseSetService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/client/{clientId}/exercisePlan/{planId}/template/{templateId}/exerciseSlot/{exerciseSlotId}/week/{weekId}/exerciseSet")
public class ExerciseSetController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseSetService exerciseSetService;
    
    @PostMapping("")
    public ResponseEntity<?> createOrUpdateExerciseForSet(@Valid @RequestBody ExerciseSet exerciseSet, @PathVariable Long weekId, BindingResult result,  Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exerciseSetService.createOrUpdateExerciseForSet(exerciseSet, weekId, principal.getName());

        return new ResponseEntity<>(exerciseSet, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllExerciseSetsForWeek(@PathVariable Long week, Principal principal) {
        Iterable<ExerciseSet> exerciseSets = exerciseSetService.getAllExerciseSetsForWeek(week, principal.getName());

        return new ResponseEntity<>(exerciseSets, HttpStatus.OK);
    }

    @GetMapping("/{exerciseSetId}")
    public ResponseEntity<?> getExerciseSetById(@PathVariable Long exerciseSetId, Principal principal){
        ExerciseSet exerciseSet = exerciseSetService.getExerciseSetById(exerciseSetId, principal.getName());

        return new ResponseEntity<>(exerciseSet, HttpStatus.OK);
    }

    @DeleteMapping("/{exerciseSetId}")
    public ResponseEntity<?> deleteExerciseSetById(@PathVariable Long exerciseSetId, Principal principal){
        exerciseSetService.deleteExerciseSetById(exerciseSetId, principal.getName());

        return new ResponseEntity<>("Exercise set with ID: '" + exerciseSetId + "' was deleted.", HttpStatus.OK);
    }
}

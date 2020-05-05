package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.entities.FoodSlot;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/client/{clientId}/exercisePlan/{planId}/template/{templateId}/exerciseSlot")
public class ExerciseSlotController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExerciseSlotService exerciseSlotService;

    // @route   POST /api/exercise/{planId}/{templateId}/exerciseSlot
    // @desc    Create exercise Slot for template
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createOrUpdateExerciseSlot(@Valid @RequestBody ExerciseSlot exerciseSlot, @PathVariable Long templateId, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exerciseSlotService.createOrUpdateExerciseSlot(exerciseSlot, templateId, principal.getName());

        return new ResponseEntity<>(exerciseSlot, HttpStatus.CREATED);
    }

    // @route   POST /api/exercise/{planId}/{templateId}/exerciseSlot
    // @desc    Create exercise Slot for template
    // @access  Private
    @GetMapping("")
    public Iterable<ExerciseSlot> getAllExerciseSlotsById(@PathVariable Long templateId, Principal principal){
        return exerciseSlotService.getAllExerciseSlotsByTemplateId(templateId, principal.getName());
    }

    // @route   POST /api/exercise/{planId}/{templateId}/exerciseSlot
    // @desc    Get all exercise slots from template
    // @access  Private
    @GetMapping("/{exerciseSlotId}")
    public ResponseEntity<?> getExerciseSlotById(@PathVariable Long templateId, @PathVariable Long exerciseSlotId, Principal principal){
       ExerciseSlot exerciseSlot = exerciseSlotService.getExerciseSlotById(templateId, exerciseSlotId, principal.getName());

        return new ResponseEntity<>(exerciseSlot, HttpStatus.OK);
    }

    // @route   POST api/exercise/{planId}/{templateId}/{weekId}/{exerciseSlotId}
    // @desc    Get all exercise slots from template
    // @access  Private
    @DeleteMapping("/{exerciseSlotId}")
    public ResponseEntity<?> deleteExercisesSlotById(@PathVariable Long exerciseSlotId, @PathVariable Long templateId, Principal principal){
        exerciseSlotService.deleteExerciseSlotById(exerciseSlotId, principal.getName());

        return new ResponseEntity<>("Exercise slot with ID: '" + exerciseSlotId + "' was deleted.", HttpStatus.OK);
    }

    // @route   POST api/exercise/template/:planId/template/:templateId/exerciseSlot
    // @desc    Create an exercise for exercise slot
    // @access  Private
    @PostMapping("/{exerciseSlotId}/exercise/{exerciseId}")
    public ResponseEntity<?> createExerciseForExerciseSlot(@PathVariable Long exerciseSlotId, @PathVariable Long exerciseId, Principal principal){
        exerciseSlotService.createExerciseForExerciseSlot(exerciseSlotId, exerciseId, principal.getName());

        return new ResponseEntity<>("Exercise with ID: '" + exerciseId + "' was added to ExerciseSlot with ID: '" + exerciseSlotId + "'.", HttpStatus.CREATED);
    }

    // @route   GET api/exercise/template/:planId/template/:templateId/exerciseSlot/:exerciseSlotId
    // @desc    Get template by template id
    // @access  Private
    @GetMapping("/{exerciseSlotId}/exercise/")
    public ResponseEntity<?> getExerciseFromExerciseSlot(@PathVariable Long exerciseSlotId, Principal principal){
        Exercise exercise = exerciseSlotService.getExerciseFromExerciseSlotById(exerciseSlotId, principal.getName());

        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }


    // @route   GET api/exercise/template/:planId/template/:templateId/exerciseSlot/:exerciseSlotId
    // @desc    Get template by template id
    // @access  Private
    @DeleteMapping("/{exerciseSlotId}/exercise/")
    public ResponseEntity<?> deleteExerciseForExerciseSlot(@PathVariable Long exerciseSlotId, Principal principal){
        exerciseSlotService.deleteExerciseFromExerciseSlotById(exerciseSlotId, principal.getName());

        return new ResponseEntity<>("The exercise was removed from ExerciseSlot(ID): '" + exerciseSlotId + "'.", HttpStatus.OK);
    }
}

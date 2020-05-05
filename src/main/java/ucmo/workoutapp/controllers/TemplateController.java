package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.Template;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.repositories.ExercisePlanRepository;
import ucmo.workoutapp.services.ExercisePlanService;
import ucmo.workoutapp.services.TemplateService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercisePlan/{planId}/template")
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExercisePlanService exercisePlanService;

    // @route   POST api/exercisePlan/:planId/template
    // @desc    Create template on exercise plan
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createNewTemplateForExercisePlan(@Valid @RequestBody Template template, BindingResult result, @PathVariable Long planId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        templateService.createNewTemplateForExercisePlan(template, planId, principal.getName());

        return new ResponseEntity<>(template, HttpStatus.CREATED);
    }

    // @route   GET api/exercisePlan/:planId/template
    // @desc    Get all templates from planId
    // @access  Private
    @GetMapping("")
    public Iterable<Template> getAllTemplatesById(@PathVariable Long planId, Principal principal){

        return templateService.getAllTemplatesById(planId, principal.getName());
    }

    // @route   GET api/exercise/template/:planId/template/:templateId
    // @desc    Get template by template id
    // @access  Private
    @GetMapping("/{templateId}")
    public ResponseEntity<?> getTemplateById(@PathVariable Long planId, @PathVariable Long templateId, Principal principal){
        Template template = templateService.getTemplateById(planId, templateId, principal.getName());

        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    // @route   DELETE api/exercise/template/:planId/template/:templateId
    // @desc    Delete template by template id
    // @access  Private
    @DeleteMapping("/{templateId}")
    public ResponseEntity<?> deleteTemplateFromExercisePlan(@PathVariable Long planId, @PathVariable Long templateId, Principal principal){
        templateService.deleteTemplateFromExercisePlan(planId, templateId, principal.getName());

        return new ResponseEntity<>("Template with ID: '" + templateId + "' was deleted.", HttpStatus.OK);
    }


}

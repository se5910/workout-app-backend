package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.MealPlanService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/client/{clientId}/mealPlan")
public class MealPlanController {
    @Autowired
    private MealPlanService mealPlanService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // @route   POST api/mealPlan
    // @desc    Create New meal plan
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> saveOrUpdateMealPlan(@Valid @RequestBody MealPlan mealPlan, @PathVariable Long clientId, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        mealPlanService.SaveOrUpdateMealPlan(clientId, mealPlan, principal.getName());
        return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);

    }

    // @route   GET api/mealPlan
    // @desc    Get all meal plans of user
    // @access  Private
    @GetMapping("")
    public  Iterable<MealPlan> getAllMealPlans(@PathVariable Long clientId, Principal principal) {
        return mealPlanService.findAllMealPlans(clientId, principal.getName());
    }

    // @route   GET api/mealPlan/:planId
    // @desc    Get meal plan by id
    // @access  Private
    @GetMapping("/{planId}")
    public MealPlan getMealPlanById(@PathVariable Long clientId, @PathVariable Long planId, Principal principal) {
       return mealPlanService.getMealPlanById(clientId, planId, principal.getName());

    }

    // @route   DELETE api/mealPlan/:planId
    // @desc    Delete meal plan by id
    // @access  Private
    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deleteMealPlanById(@PathVariable Long clientId, @PathVariable Long planId, Principal principal) {
        mealPlanService.deleteByMealPlanId(clientId, planId, principal.getName());

        return new ResponseEntity<>("Plan with ID: '" + planId + "' was deleted.", HttpStatus.OK);
    }

    // @route   GET api/mealPlan/client/:cliendId
    // @desc    Get all exercise plans of client
    // @access  Private
//    @GetMapping("")
//    public Iterable<MealPlan> findAllMealPlansOfClient(@PathVariable Long clientId, @PathVariable Long planId, Principal coach) {
//        return mealPlanService.findAllMealPlansOfClient(clientId, coach.getName());
//    }
}

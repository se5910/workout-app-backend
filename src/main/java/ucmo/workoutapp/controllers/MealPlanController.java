package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.MealPlanService;
import ucmo.workoutapp.services.MealService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/mealplan")
public class MealPlanController {
    @Autowired
    private MealPlanService mealPlanService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private MealService mealService;

    // @route   POST api/mealplan
    // @desc    Create New meal plan
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createNewMealPlan(@Valid @RequestBody MealPlan mealPlan, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        mealPlanService.SaveOrUpdateMealPlan(mealPlan, principal.getName());
        return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);

    }

    // @route   GET api/mealplan/all
    // @desc    Get all meal plans of user
    // @access  Private
    @GetMapping("")
    public  Iterable<MealPlan> getAllMealPlans(Principal principal) {
        return mealPlanService.findAllMealPlans(principal.getName());
    }

    // @route   GET api/mealplan/:planId
    // @desc    Get meal plan by id
    // @access  Private
    @GetMapping("/{planId}")
    public ResponseEntity<?> getMealPlanById(@PathVariable Long planId, Principal principal) {
        MealPlan plan = mealPlanService.getMealPlanById(planId, principal.getName());

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    // @route   DELETE api/mealplan/:planId
    // @desc    Delete meal plan by id
    // @access  Private
    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deleteMealPlanById(@PathVariable Long planId, Principal principal) {
        mealPlanService.deleteByMealPlanId(planId, principal.getName());

        return new ResponseEntity<>("Plan with ID: '" + planId + "' was deleted.", HttpStatus.OK);
    }



}

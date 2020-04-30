package ucmo.workoutapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.MealService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/mealplan/{planId}/meal")
public class MealController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private MealService mealService;

    // @route   POST api/mealplan/:planid/meal
    // @desc    Create meal for meal plan
    // @access  Private
    @PostMapping("")
    public ResponseEntity<?> createMealForMealPlan(@Valid @RequestBody Meal meal, BindingResult result, @PathVariable Long planId, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        mealService.createMealForMealPlan(meal, planId, principal.getName());

        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }


    @GetMapping("/{mealId}")
    public ResponseEntity<?> findMealByMealPlanId(@PathVariable Long planId, @PathVariable Long mealId, Principal principal){
        Iterable<Meal> meals = mealService.getMealsByMealPlanId(planId,mealId,principal.getName());

        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<?> deleteMealByMealPlanId(@PathVariable Long mealId, Principal principal){
         mealService.deleteMealById(mealId,principal.getName());

        return new ResponseEntity<>("Meal with ID: '" + mealId + "' was deleted.", HttpStatus.OK);
    }
}
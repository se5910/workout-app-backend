package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.MealPlanService;
import ucmo.workoutapp.services.MealService;

import javax.naming.Binding;
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

    @PostMapping("")
    public ResponseEntity<?> createNewMealPlan(@Valid @RequestBody MealPlan mealPlan, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        mealPlanService.SaveOrUpdateMealPlan(mealPlan, principal.getName());
        return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public  Iterable<MealPlan> getAllMealPlans(Principal principal) {
        return mealPlanService.findAllMealPlans(principal.getName());
    }

    @GetMapping("/{planId}")
    public ResponseEntity<?> getMealPlanById(@PathVariable Long planId, Principal principal) {
        MealPlan plan = mealPlanService.getMealPlanById(planId, principal.getName());

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deleteMealPlanById(@PathVariable Long planId, Principal principal) {
        mealPlanService.deleteByExercisePlanId(planId, principal.getName());

        return new ResponseEntity<>("Plan with ID: '" + planId + "' was deleted.", HttpStatus.OK);
    }

    @PostMapping("/{planId}/meal")
    public ResponseEntity<?> createMealForMealPlan(@Valid @RequestBody Meal meal, BindingResult result, @PathVariable Long planId, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        mealService.createMealForMealPlan(meal, planId, principal.getName());

        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }
}

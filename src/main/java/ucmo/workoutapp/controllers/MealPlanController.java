package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.MealPlanService;

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
}

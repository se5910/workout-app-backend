package ucmo.workoutapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.services.MealPlanService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/meal")
public class MealPlanController {
    @Autowired
    private MealPlanService mealPlanService;

    @PostMapping("")
    public ResponseEntity<?> createNewExercisePlan(@Valid @RequestBody MealPlan mealPlan, BindingResult result, Principal principal) {
        mealPlanService.SaveOrUpdateExercisePlan(mealPlan, principal.getName());
        return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public  Iterable<MealPlan> getAllExercisePlans(Principal principal) {
        return mealPlanService.findAllMealPlans(principal.getName());
    }
}

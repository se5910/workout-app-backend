package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.FoodSlot;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.FoodSlotService;
import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/mealplan/{planId}/meal/{mealId}/foodslot")
public class FoodSlotController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private FoodSlotService foodSlotService;


    @PostMapping("")
    public ResponseEntity<?> createFoodSlotForMeal(@Valid @RequestBody FoodSlot foodSlot, BindingResult result, @PathVariable Long mealId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        foodSlotService.createFoodSlotForMeal(foodSlot, mealId, principal.getName());

        return new ResponseEntity<>(foodSlot, HttpStatus.CREATED);
    }

    @GetMapping("/{foodSlotId}")
    public ResponseEntity<?> getFoodSlotById(@PathVariable Long foodSlotId, Principal principal){
        FoodSlot foodSlot = foodSlotService.getFoodSlotById(foodSlotId , principal.getName());

        return new ResponseEntity<>(foodSlot, HttpStatus.OK);
    }

    @DeleteMapping("/{foodSlotId}")
    public ResponseEntity<?> deleteMealByMealPlanId(@PathVariable Long foodSlotId, Principal principal){
        foodSlotService.deleteFoodSlotById(foodSlotId,principal.getName());

        return new ResponseEntity<>("Meal slot with ID: '" + foodSlotId + "' was deleted.", HttpStatus.OK);
    }

    @GetMapping("")
    public Iterable<FoodSlot> getAllFoodSlotsByMealId(@PathVariable Long mealId, Principal principal){
        return foodSlotService.getAllFoodSlotsByMealId(mealId, principal.getName());
    }
}

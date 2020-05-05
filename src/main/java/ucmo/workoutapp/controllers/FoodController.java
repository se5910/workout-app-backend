package ucmo.workoutapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Food;
import ucmo.workoutapp.exceptions.ItemNotFoundException;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.FoodService;
import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private FoodService foodService;

    @PostMapping
    public ResponseEntity<?> createFood(@Valid @RequestBody Food food, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        foodService.createFood(food, principal.getName());
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<?> getFoodById(@PathVariable Long foodId){
        Food food = foodService.getFoodById(foodId);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<?> deleteFoodById(@PathVariable Long foodId, Principal principal){
        foodService.deleteFoodById(foodId, principal.getName());
        return new ResponseEntity<>("Food with ID: " + foodId + " was deleted.",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFood(Principal principal){
        Iterable<Food> foods = foodService.findAllFood(principal.getName());
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<?> editFoodById(@PathVariable Long foodId, @RequestBody Food foodUpdate){
        Food food = foodService.getFoodById(foodId);
        if (food == null)
            throw new ItemNotFoundException("Food cant be updated, Food with id: " + foodId + " was not found");

        food.setName(foodUpdate.getName());
        food.setServings(foodUpdate.getServings());
        food.setQty(foodUpdate.getQty());
        food.setUnit(foodUpdate.getUnit());
        food.setCalories(foodUpdate.getCalories());
        food.setProtein(foodUpdate.getProtein());
        food.setCarbs(foodUpdate.getCarbs());
        food.setFat(foodUpdate.getFat());
        food.setFiber(foodUpdate.getFiber());

        return new ResponseEntity<>(food,HttpStatus.OK);
    }
}

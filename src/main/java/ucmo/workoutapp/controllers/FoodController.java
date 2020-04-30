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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private FoodService foodService;

    @GetMapping("/{foodId}")
    public ResponseEntity<?> getFoodById(@PathVariable Long foodId){
        Food food = foodService.getFoodById(foodId);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Food> getAllFood(){
        return foodService.findAllFood();
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<?> deleteFoodById(@PathVariable Long foodId){
        foodService.deleteFoodById(foodId);
        return new ResponseEntity<>("Food with ID: " + foodId + " was deleted.",HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createFood(@Valid @RequestBody Food food, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        foodService.createFood(food);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
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

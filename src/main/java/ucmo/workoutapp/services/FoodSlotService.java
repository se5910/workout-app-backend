package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Food;
import ucmo.workoutapp.entities.FoodSlot;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.repositories.FoodRepository;
import ucmo.workoutapp.repositories.FoodSlotRepository;
import ucmo.workoutapp.repositories.MealRepository;

@Service
public class FoodSlotService {

    @Autowired
    private FoodSlotRepository foodSlotRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;

    public FoodSlot createFoodSlotForMeal(FoodSlot foodSlot, Long mealId, String username){
        Meal meal = mealRepository.getById(mealId);
        foodSlot.setMeal(meal);

        return foodSlotRepository.save(foodSlot);
    }

    public FoodSlot getFoodSlotById(Long foodSlotId, String username){
        return foodSlotRepository.getById(foodSlotId);
    }

    public void deleteFoodSlotById(Long foodSlotId, String username){
         foodSlotRepository.deleteById(foodSlotId);
    }

    public Iterable<FoodSlot> getAllFoodSlotsByMealId(Long mealId, String username){
        Meal meal = mealRepository.getById(mealId);

        return meal.getFoodSlots();
    }

    // Returns food slot because we are actually changing the food slot by adding a food to it
    public FoodSlot putFoodIntoFoodSlot(Long foodSlotId, Long foodId, String username){
        FoodSlot foodSlot = foodSlotRepository.getById(foodSlotId);

        foodSlot.setFoodId(foodId);

        return foodSlotRepository.save(foodSlot);

    }

    // Get the food from the food slot by id
    public Food getFoodFromFoodSlot(Long foodSlotId, String username){
        return foodRepository.getById(foodSlotRepository.getById(foodSlotId).getFoodId());

    }

    // Set the food of the food slot to "null" to delete it
    public void deleteFoodFromFoodSlotById(Long foodSlotId, String username){
        // Get the food slot from the database
        FoodSlot foodSlot = foodSlotRepository.getById(foodSlotId);

        // "Delete" the food by setting the food in the slot to null
        foodSlot.setFoodId(null);
    }
}

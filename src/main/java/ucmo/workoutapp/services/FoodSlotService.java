package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.FoodSlot;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.repositories.FoodSlotRepository;
import ucmo.workoutapp.repositories.MealRepository;

@Service
public class FoodSlotService {

    @Autowired
    private FoodSlotRepository foodSlotRepository;

    @Autowired
    private MealRepository mealRepository;

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
        return foodSlotRepository.getAllByMeal_Id(mealId);
    }
}

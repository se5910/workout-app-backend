package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Food;
import ucmo.workoutapp.entities.FoodSlot;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.FoodRepository;
import ucmo.workoutapp.repositories.FoodSlotRepository;
import ucmo.workoutapp.repositories.MealRepository;
import ucmo.workoutapp.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class FoodSlotService {

    @Autowired
    private FoodSlotRepository foodSlotRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    public FoodSlot createOrUpdateFoodSlot(FoodSlot foodSlot, Long mealId, String username){
        Meal meal = mealRepository.getById(mealId);
        User request = userRepository.findByUsername(username);

        if (meal == null) {
            throw new EntityNotFoundException("Meal is null");
        }

        if (foodSlot == null) {
            throw new PlanNotFoundException("FoodSlot is null");
        }

        if (!request.isCoach() || !meal.getMealPlan().getClient().getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client or you are not a coach at all.");
        }

        if (foodSlot.getId() != null) {
            FoodSlot existingFoodSlot = foodSlotRepository.getById(foodSlot.getId());

            return foodSlotRepository.save(existingFoodSlot);
        }

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

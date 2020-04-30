package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.MealPlanRepository;
import ucmo.workoutapp.repositories.MealRepository;
import ucmo.workoutapp.repositories.UserRepository;

@Service
public class MealService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Meal createMealForMealPlan(Meal meal, Long planId, String username){
        MealPlan mealPlan = mealPlanRepository.getByPlanId(planId);
        meal.setMealPlan(mealPlan);
        meal.setName(meal.getName());

        return mealRepository.save(meal);
    }

    //get all the meals for a given plan
    public Iterable<Meal> getMealsByMealPlanId(Long planId, Long mealId, String username){
        return mealRepository.getMealsByMealPlan(planId);
    }

    //get one meal
    public Meal getOneMealById(Long planId, Long mealId, String username) {
        return mealRepository.getById(mealId);
    }

    public void deleteMealById(Long mealId,String username){
         mealRepository.deleteById(mealId);
    }
}

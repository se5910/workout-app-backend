package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.repositories.MealPlanRepository;
import ucmo.workoutapp.repositories.MealRepository;

@Service
public class MealService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private MealRepository mealRepository;

    public Meal createMealForMealPlan(Meal meal, Long planId, String username){
        MealPlan mealPlan = mealPlanRepository.getByPlanId(planId);
        meal.setMealPlan(mealPlan);
        meal.setName(meal.getName());

        return mealRepository.save(meal);
    }

    //get meal for a plan
    public Meal getMealById(Long planId, Long mealId, String username){
        return mealRepository.getById(mealId);
    }

    public void deleteMealById(Long mealId,String username){
         mealRepository.deleteById(mealId);
    }

    public Iterable<Meal> getallMealsForMealPlanById(Long planId, String username){
        return mealRepository.getAllByMealPlan(planId);
    }
}

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
    MealRepository mealRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    public Meal createMealForMealPlan(Meal meal, Long planId, String username){
        MealPlan mealPlan = mealPlanRepository.getByPlanId(planId);
        meal.setMealPlan(mealPlan);
        meal.setName(meal.getName());

        return mealRepository.save(meal);
    }
}

package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.repositories.MealPlanRepository;

@Service
public class MealPlanService {
    @Autowired
    private MealPlanRepository mealPlanRepository;

    public MealPlan SaveOrUpdateExercisePlan(MealPlan mealPlan, Long client_id){
        if (mealPlan.getPlanId() != null){
            MealPlan existingExercisePlan = mealPlanRepository.getByPlanId(mealPlan.getPlanId());
        }

        return mealPlanRepository.save(mealPlan);
    }
}

package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.Meal;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.MealPlanRepository;
import ucmo.workoutapp.repositories.MealRepository;
import ucmo.workoutapp.repositories.UserRepository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityNotFoundException;

@Service
public class MealService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    public Meal createMealForMealPlan(Meal meal, Long planId, String username){
        MealPlan mealPlan = mealPlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);

        if (meal == null){
            throw new EntityNotFoundException("Template is null");
        }

        if (mealPlan == null) {
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (!request.isCoach()){
            throw new CoachNotFoundException("You are not a coach");
        }

        if(!mealPlan.getClient().getUser().getUsername().equals(username) || !mealPlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are no the client or you are not the client's coach");
        }

        meal.setMealPlan(mealPlan);
        meal.setName(meal.getName());

        return mealRepository.save(meal);
    }

    //get meal for a plan
    public Meal getMealById(Long planId, Long mealId, String username){
        MealPlan mealPlan = mealPlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);

        if(!mealPlan.getClient().getUser().getUsername().equals(username) || !mealPlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are no the client or you are not the client's coach");
        }

        return mealRepository.getById(mealId);
    }

    public void deleteMealById(Long planId, Long mealId, String username){
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()){
            throw new CoachNotFoundException("You are not a coach");
        }

         mealRepository.delete(getMealById(mealId, planId, username));
    }

    public Iterable<Meal> getAllMealsForMealPlanById(Long planId, String username){
        MealPlan mealPlan = mealPlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);

        if(!mealPlan.getClient().getUser().getUsername().equals(username) || !mealPlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are no the client or you are not the client's coach");
        }

        return mealRepository.getAllByMealPlan(planId);
    }
}

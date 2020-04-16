package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.MealPlanRepository;
import ucmo.workoutapp.repositories.UserRepository;

@Service
public class MealPlanService {
    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    public MealPlan SaveOrUpdateExercisePlan(MealPlan mealPlan, String username){
        if (mealPlan.getPlanId() != null){
            MealPlan existingExercisePlan = mealPlanRepository.getByPlanId(mealPlan.getPlanId());
        }

        return mealPlanRepository.save(mealPlan);
    }

    public Iterable<MealPlan> findAllMealPlans(String username) {
        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);

        return mealPlanRepository.findAllByClient(client);
    }
}

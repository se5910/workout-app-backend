package ucmo.workoutapp.services;

import jdk.nashorn.internal.parser.Lexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.MealPlan;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.MealPlanRepository;
import ucmo.workoutapp.repositories.UserRepository;


@Service
public class MealPlanService {
    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public MealPlan SaveOrUpdateMealPlan(MealPlan mealPlan, String username) {
        if (mealPlan.getPlanId() != null) {
            MealPlan existingPlan = mealPlanRepository.getByPlanId(mealPlan.getPlanId());

            User user = userRepository.findByUsername(username);
            Client client = clientRepository.getByUser(user);

            if (existingPlan != null && (!existingPlan.getClient().equals(client))) {
                throw new PlanNotFoundException("Meal Plan not found in your account");
            } else if (existingPlan == null) {
                throw new PlanNotFoundException("Plan with ID: '" + mealPlan.getPlanId() + "' cannot be updated because it doesn't exist");
            }
        }

        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);

        mealPlan.setClient(client);
        mealPlan.setName(mealPlan.getName());

        return mealPlanRepository.save(mealPlan);
    }

    public Iterable<MealPlan> findAllMealPlans(String username) {
        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);

        return mealPlanRepository.findAllByClient(client);
    }
}

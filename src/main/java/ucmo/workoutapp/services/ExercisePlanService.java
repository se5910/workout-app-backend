package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.entities.Plan;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.ExercisePlanRepository;
import ucmo.workoutapp.repositories.UserRepository;

import javax.validation.constraints.Null;

@Service
public class ExercisePlanService {
    @Autowired
    private ExercisePlanRepository exercisePlanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public ExercisePlan SaveOrUpdateExercisePlan(ExercisePlan exercisePlan, String username){
        if (exercisePlan.getPlanId() != null) {
            ExercisePlan existingPlan = exercisePlanRepository.getByPlanId(exercisePlan.getPlanId());

            User user = userRepository.findByUsername(username);
            Client client = clientRepository.getByUser(user);

            if (existingPlan != null && (!existingPlan.getClient().equals(client))) {
                throw new PlanNotFoundException("Exercise Plan not found in your account");
            } else if (existingPlan == null) {
                throw new PlanNotFoundException("Plan with ID: '" + exercisePlan.getPlanId() + "' cannot be updated because it doesn't exist");
            }
        }

        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);

        exercisePlan.setClient(client);
        exercisePlan.setName(exercisePlan.getName());

        return exercisePlanRepository.save(exercisePlan);

    }

    public Iterable<ExercisePlan> findAllExercisePlans(String username) {
        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);

        return exercisePlanRepository.findAllByClient(client);
    }
}

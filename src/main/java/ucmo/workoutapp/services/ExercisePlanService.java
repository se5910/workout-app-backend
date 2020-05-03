package ucmo.workoutapp.services;

import org.omg.CORBA.portable.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
import ucmo.workoutapp.exceptions.ItemNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.ExercisePlanRepository;
import ucmo.workoutapp.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;

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

        return exercisePlanRepository.save(exercisePlan);

    }

    public Iterable<ExercisePlan> findAllExercisePlans(String username) {
        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);

        return exercisePlanRepository.findAllByClient(client);
    }

    public ExercisePlan findExercisePlanById(Long planId, String username) {
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);

        if (exercisePlan == null) {
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }

        if (!exercisePlan.getClient().equals(client)) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return exercisePlan;
    }

    public void deleteByExercisePlanId(Long planId, String username) {
        System.out.println(planId);
        exercisePlanRepository.delete(findExercisePlanById(planId, username));

    }

    public Iterable<ExercisePlan> findAllExercisePlansOfClient(Long clientId, String coach) {
        Client client = clientRepository.getById(clientId);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }

        if (!client.getCoach().equals(coach)) {
            throw new ClientNotFoundException("No clients found in your account");
        }


        return exercisePlanRepository.findAllByClient(client);
    }
}

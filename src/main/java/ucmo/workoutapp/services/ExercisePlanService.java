package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
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

    public ExercisePlan SaveOrUpdateExercisePlan(Long clientId, ExercisePlan exercisePlan, String coach){
        Client client = clientRepository.getById(clientId);

        User request = userRepository.findByUsername(coach);
        if(!request.isCoach()) {
            throw new CoachNotFoundException("The account is not a coach account");
        }
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }

        if (!client.getCoach().equals(coach)) {
            throw new CoachNotFoundException("Client not associated with this coach");
        }

        if (exercisePlan.getPlanId() != null) {
            ExercisePlan existingPlan = exercisePlanRepository.getByPlanId(exercisePlan.getPlanId());

            if (existingPlan != null && (!existingPlan.getClient().equals(client))) {
                throw new PlanNotFoundException("Exercise Plan not found for this client");
            } else if (existingPlan == null) {
                throw new PlanNotFoundException("Plan with ID: '" + exercisePlan.getPlanId() + "' cannot be updated because it doesn't exist");
            }

            return exercisePlanRepository.save(exercisePlan);
        }

        exercisePlan.setClient(client);

        return exercisePlanRepository.save(exercisePlan);
}


    public Iterable<ExercisePlan> getAllExercisePlans(Long clientId, String username) {
        User request = userRepository.findByUsername(username);
        Client client = clientRepository.getById(clientId);

        if ((request.isCoach() && !client.getCoach().equals(request.getUsername()))){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        // Client is null
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }

        if (!request.isCoach() && !request.getUsername().equals(client.getUser().getUsername())){
            throw new ClientNotFoundException("You are not the client of this plan");
        }

        return exercisePlanRepository.findAllByClient(client);
    }

    public ExercisePlan getExercisePlanById(Long clientId, Long planId, String username) {
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        Client client = clientRepository.getById(clientId);
        User request = userRepository.findByUsername(username);

        // You are a coach but not this client's coach
        if ((request.isCoach() && !client.getCoach().equals(request.getUsername()))){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        // Exercise plan is null
        if (exercisePlan == null) {
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        // Client is null
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }

        // You are a client but this plan does not belong to you
        if (!exercisePlan.getClient().equals(client)) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return exercisePlan;
    }

    public void deleteByExercisePlanId(Long clientId, Long planId, String username) {
        exercisePlanRepository.delete(getExercisePlanById(clientId, planId, username));

    }
}

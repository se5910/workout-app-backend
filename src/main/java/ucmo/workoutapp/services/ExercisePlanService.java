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

    public ExercisePlan createOrUpdateExercisePlan(Long clientId, ExercisePlan exercisePlan, String username){
        Client client = clientRepository.getById(clientId);
        User request = userRepository.findByUsername(username);

        if (exercisePlan == null) {
            throw new EntityNotFoundException("Exercise Plan not found");
        }

        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }

        if (!request.isCoach() || !client.getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client or you are not a coach at all.");
        }

        if (exercisePlan.getPlanId() != null) {
            ExercisePlan existingPlan = exercisePlanRepository.getByPlanId(exercisePlan.getPlanId());

            return exercisePlanRepository.save(existingPlan);
        }

        exercisePlan.setClient(client);

        return exercisePlanRepository.save(exercisePlan);
    }

    public Iterable<ExercisePlan> getAllExercisePlans(Long clientId, String username) {
        User request = userRepository.findByUsername(username);
        Client client = clientRepository.getById(clientId);

        // Client is null
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }

        if ((request.isCoach() && !client.getCoach().equals(request.getUsername()))){
            throw new CoachNotFoundException("You are not the coach of this client");
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

        // Exercise plan is null
        if (exercisePlan == null) {
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        // Client is null
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }

        // You are a coach but not this client's coach
        if ((request.isCoach() && !client.getCoach().equals(request.getUsername()))){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        // You are a client but this plan does not belong to you
        if (request.isCoach() && !exercisePlan.getClient().equals(client)) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return exercisePlan;
    }

    public void deleteByExercisePlanId(Long clientId, Long planId, String username) {
        User request = userRepository.findByUsername(username);

        // Prevent !coach from going any further
        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot delete a plan");
        }

        // Utilize getTemplateById checks to ensure coach username matches client username from plan
        ExercisePlan exercisePlan = getExercisePlanById(clientId, planId, username);

        exercisePlanRepository.delete(exercisePlan);
    }
}

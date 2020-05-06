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
        // Get a client object via the ID passed in.
        Client client = clientRepository.getById(clientId);

        // Gather the user making the request by looking for its username.
        User request = userRepository.findByUsername(username);

        // If the exercisePlan being sent from the API call is null, throw an error.
        if (exercisePlan == null) {
            throw new EntityNotFoundException("Exercise Plan not found");
        }

        // If the client could not be found by ID, throw an error.
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }

        // If the user requesting this information is NOT a coach, and the requesting user (who is also a Client; see Client Entity for details)
        // ... IS NOT the same client as the client data being collected, throw an error.
        if (!request.isCoach() || !client.getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client or you are not a coach at all.");
        }

        // If an ExercisePlan already exists, Update it with passed in information from exercisePlan
        if (exercisePlan.getPlanId() != null) {
            ExercisePlan existingPlan = exercisePlanRepository.getByPlanId(exercisePlan.getPlanId());

            // Set data that was passed in from header
            existingPlan.setName(exercisePlan.getName());
            existingPlan.setClient(exercisePlan.getClient());
            existingPlan.setTemplates(exercisePlan.getTemplates());
            existingPlan.setPlanId(exercisePlan.getPlanId());

            // Save the object in the database
            return exercisePlanRepository.save(existingPlan);
        }

        // Logic if not previously created, set the client (to associate it in the DB).
        exercisePlan.setClient(client);

        // Save the object in the database
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

        // Utilize getTemplateById checks to insure coach username matches client username from plan
        ExercisePlan exercisePlan = getExercisePlanById(clientId, planId, username);

        exercisePlanRepository.delete(exercisePlan);
    }
}

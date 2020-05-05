package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.MealPlanRepository;
import ucmo.workoutapp.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class MealPlanService {
    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public MealPlan SaveOrUpdateMealPlan(Long clientId, MealPlan mealPlan, String username) {
        Client client = clientRepository.getById(clientId);
        User request = userRepository.findByUsername(username);

        if(!request.isCoach()) {
            throw new CoachNotFoundException("The account is not a coach account");
        }
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }

        if (mealPlan == null) {
            throw new EntityNotFoundException("Meal plan not found");
        }

        if (!client.getCoach().equals(username)) {
            throw new CoachNotFoundException("Client not associated with this coach");
        }

        if (mealPlan.getPlanId() != null) {
            MealPlan existingPlan = mealPlanRepository.getByPlanId(mealPlan.getPlanId());

            if (existingPlan != null && (!existingPlan.getClient().equals(client))) {
                throw new PlanNotFoundException("Meal Plan not found in your account");
            } else if (existingPlan == null) {
                throw new PlanNotFoundException("Plan with ID: '" + mealPlan.getPlanId() + "' cannot be updated because it doesn't exist");
            }

            return mealPlanRepository.save(existingPlan);
        }

        mealPlan.setClient(client);

        return mealPlanRepository.save(mealPlan);
    }

    public Iterable<MealPlan> findAllMealPlans(Long clientId, String username) {
        User request = userRepository.findByUsername(username);
        Client client = clientRepository.getById(clientId);

        if((request.isCoach() && !client.getCoach().equals(request.getUsername()))){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }

        if (!request.isCoach() && !request.getUsername().equals(client.getUser().getUsername())){
            throw new ClientNotFoundException("You are not the client of this plan");
        }

        return mealPlanRepository.findAllByClient(client);
    }

    public MealPlan getMealPlanById(Long clientId, Long planId, String username) {
        MealPlan mealPlan = mealPlanRepository.getByPlanId(planId);
        Client client = clientRepository.getById(clientId);
        User request = userRepository.findByUsername(username);

        if ((request.isCoach() && !client.getCoach().equals(request.getUsername()))){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (mealPlan == null) {
            throw new PlanNotFoundException("Meal Plan does not exist");
        }

        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }

        if (!mealPlan.getClient().equals(client)) {
            throw new PlanNotFoundException("This plan does not belong to your account");
        }

        return mealPlan;
    }

    public void deleteByMealPlanId(Long clientId, Long planId, String username) {
        mealPlanRepository.delete(getMealPlanById(clientId, planId, username));
    }
}

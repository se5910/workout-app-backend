package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.TemplateRepository;
import ucmo.workoutapp.repositories.ExercisePlanRepository;
import ucmo.workoutapp.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class TemplateService {
    @Autowired
    ExercisePlanRepository exercisePlanRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    public Template createNewTemplateForExercisePlan(Template template, Long planId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);

        if (template == null){
            throw new EntityNotFoundException("Template is null");
        }

        if (exercisePlan == null) {
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (!request.isCoach()){
            throw new CoachNotFoundException("You are not a coach");
        }

        if(!exercisePlan.getClient().getUser().getUsername().equals(username) || !exercisePlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("Wrong client or coach permissions!");
        }

        if (template.getId() != null){
            Template existingTemplate = templateRepository.getById(template.getId());

            if (existingTemplate != null && !existingTemplate.getExercisePlan().getClient().equals(clientRepository.getByUser(request))) {
                throw new PlanNotFoundException("Exercise Plan not found in your account");
            } else if (existingTemplate == null){
                throw new PlanNotFoundException("Plan with ID: " + exercisePlan.getPlanId() + "' cannot be updated because it doesn't exist");
            }

            return templateRepository.save(existingTemplate);
        }

        template.setExercisePlan(exercisePlan);

        return templateRepository.save(template);
    }

    public Template getTemplateById(Long planId, Long templateId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        return templateRepository.getById(templateId);

    }

    public void deleteTemplateFromExercisePlan(Long planId, Long templateId, String username){
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot delete a plan");
        }

        templateRepository.delete(getTemplateById(planId, templateId, username));
    }

    public Iterable<Template> getAllTemplatesById(Long planId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);

        if(!exercisePlan.getClient().getUser().getUsername().equals(username) || !exercisePlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("Wrong client or coach permissions!");
        }

        return exercisePlan.getTemplates();
    }
}

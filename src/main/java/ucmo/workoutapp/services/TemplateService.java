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

    public Template createOrUpdateTemplate(Template template, Long planId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);

        if (template == null) {
            throw new EntityNotFoundException("Template is null");
        }

        if (exercisePlan == null) {
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach you cannot create or update a template");
        }

        if (request.isCoach() && !exercisePlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (template.getId() != null){
            Template existingTemplate = templateRepository.getById(template.getId());

            existingTemplate.setName(template.getName());
            existingTemplate.setPhase(template.getPhase());
            existingTemplate.setWorkoutType(template.getWorkoutType());

            return templateRepository.save(existingTemplate);
        }

        template.setExercisePlan(exercisePlan);

        return templateRepository.save(template);
    }

    public Template getTemplateById(Long planId, Long templateId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);
        Template template = templateRepository.getById(templateId);

        if (exercisePlan == null){
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (template == null) {
            throw new EntityNotFoundException("Template Not found");
        }

        if (request.isCoach() && !exercisePlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !exercisePlan.getClient().equals(clientRepository.getByUser(request))) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return template;

    }

    public void deleteTemplateFromExercisePlan(Long planId, Long templateId, String username){
        User request = userRepository.findByUsername(username);

        // Prevent !coach from going any further
        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot delete a plan");
        }

        // Utilize getTemplateById checks to insure coach username matches client username from plan
        Template template = getTemplateById(planId, templateId, username);

        templateRepository.delete(template);
    }

    public Iterable<Template> getAllTemplatesById(Long planId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        User request = userRepository.findByUsername(username);

        if (exercisePlan == null){
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (request.isCoach() && !exercisePlan.getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !exercisePlan.getClient().equals(clientRepository.getByUser(request))) {
            throw new PlanNotFoundException("Exercise Plan not found in your account. Unable to locate templates.");
        }

        return exercisePlan.getTemplates();
    }
}

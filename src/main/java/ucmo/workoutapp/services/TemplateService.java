package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
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
        Client client = clientRepository.getByUser(userRepository.findByUsername(username));

        if (exercisePlan == null) {
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }

        if (template == null){
            throw new EntityNotFoundException("Template is null");
        }

        if (!exercisePlan.getClient().equals(client)) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        template.setExercisePlan(exercisePlan);

        return templateRepository.save(template);
    }

    public Iterable<Template> getAllTemplatesById(Long planId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        Client client = clientRepository.getByUser(userRepository.findByUsername(username));
        if (!exercisePlan.getClient().equals(client)) {
            throw new PlanNotFoundException("Template not found in your account.");
        }

        return exercisePlan.getTemplates();
    }

    public Template getTemplateById(Long templateId, String username){
        return templateRepository.getById(templateId);

    }

    public void deleteTemplateFromExercisePlan(Long templateId, String username){
      templateRepository.delete(getTemplateById(templateId, username));

    }

}

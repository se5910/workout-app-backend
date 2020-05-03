package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.TemplateRepository;
import ucmo.workoutapp.repositories.ExercisePlanRepository;
import ucmo.workoutapp.repositories.UserRepository;

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
        template.setExercisePlan(exercisePlan);
        template.setPhase(template.getPhase());
        template.setWorkoutType(template.getWorkoutType());
        template.setName(template.getName());

        return templateRepository.save(template);
    }

    public Template getTemplateById(Long templateId, String username){

        return templateRepository.getById(templateId);
    }

    public void deleteTemplateFromExercisePlan(Long templateId, String username){
      templateRepository.delete(getTemplateById(templateId, username));

    }

}

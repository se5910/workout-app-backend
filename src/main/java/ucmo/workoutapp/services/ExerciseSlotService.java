package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.*;

import javax.persistence.EntityNotFoundException;

@Service
public class ExerciseSlotService {
    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ExerciseSlotRepository exerciseSlotRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ExerciseSlot createOrUpdateExerciseSlot(ExerciseSlot exerciseSlot, Long templateId, String username){
        Template template = templateRepository.getById(templateId);
        User request = userRepository.findByUsername(username);

        if (exerciseSlot == null) {
            throw new EntityNotFoundException("ExerciseSlot is null");
        }

        if (template == null) {
            throw new EntityNotFoundException("Template does not exist");
        }

        if (!request.isCoach() || !template.getExercisePlan().getClient().getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client or you are not a coach at all.");
        }

        if (exerciseSlot.getId() != null){
            ExerciseSlot existingExerciseSlot = exerciseSlotRepository.getById(exerciseSlot.getId());

            return exerciseSlotRepository.save(existingExerciseSlot);
        }

        exerciseSlot.setTemplate(template);

        return exerciseSlotRepository.save(exerciseSlot);
    }

    public Iterable<ExerciseSlot> getAllExerciseSlotsById(Long templateId, String username){
        Template template = templateRepository.getById(templateId);
        User request = userRepository.findByUsername(username);

        if (template == null) {
            throw new PlanNotFoundException("Template does not exist");
        }

        if (request.isCoach() && !template.getExercisePlan().getClient().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !template.getExercisePlan().getClient().equals(request.getUsername())) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return template.getExerciseSlots();
    }

    public ExerciseSlot getExerciseSlotById(Long templateId, Long exerciseSlotId, String username) {
        Template template = templateRepository.getById(templateId);
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        User request = userRepository.findByUsername(username);

        if (template == null){
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (exerciseSlot == null) {
            throw new EntityNotFoundException("Template Not found");
        }

        if (request.isCoach() && !template.getExercisePlan().getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !template.getExercisePlan().getClient().equals(clientRepository.getByUser(request))) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return exerciseSlot;
    }

    public void deleteExerciseSlotById(Long templateId, Long exerciseSlotId, String username) {
        User request = userRepository.findByUsername(username);

        // Prevent !coach from going any further
        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot delete a plan");
        }

        // Utilize getExerciseSlot checks to ensure coach username matches client username from plan
        ExerciseSlot exerciseSlot = getExerciseSlotById(templateId, exerciseSlotId, username);

        exerciseSlotRepository.delete(exerciseSlot);

    }

    // Returns exercise slot because we are actually changing the exercise slot by adding an exercise to it
    public ExerciseSlot createOrUpdateExerciseForExerciseSlot(Long exerciseSlotId, Long exerciseId, String username) {
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        Exercise exercise = exerciseRepository.getById(exerciseSlot.getExerciseId());
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach you cannot create or update a template");
        }

        if (request.isCoach() || !exerciseSlot.getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client or you are not a coach at all.");
        }

        if (exercise != null) {
            Exercise newExercise = exerciseRepository.getById(exerciseId);

            exerciseSlot.setExerciseId(newExercise.getId());

            return exerciseSlotRepository.save(exerciseSlot);
        }

        exerciseSlot.setExerciseId(exerciseId);

        return exerciseSlotRepository.save(exerciseSlot);
    }

    // Get the exercise from the exercise slot by id
    public Exercise getExerciseFromExerciseSlotById(Long exerciseSlotId, String username){
        User request = userRepository.findByUsername(username);
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);

        if (exerciseSlot == null){
            throw new PlanNotFoundException("Exercise Plan does not exist");
        }

        if (request.isCoach() && !exerciseSlot.getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !exerciseSlot.getTemplate().getExercisePlan().getClient().equals(clientRepository.getByUser(request))) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return exerciseRepository.getById(exerciseSlot.getExerciseId());

    }

    // Set the exercise of the exercise slot to "null" to delete it
    public void deleteExerciseFromExerciseSlotById(Long exerciseSlotId, String username){
        User request = userRepository.findByUsername(username);
        // Get the correct exercise slot form the database

        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot delete a plan");
        }

        // Utilize getExerciseFromExerciseSlotById checks to ensure coach username matches client username from plan
        Exercise exercise = getExerciseFromExerciseSlotById(exerciseSlotId, username);

        // "Delete" the exercise by setting the exercise in the slot to null
        exerciseSlot.setExerciseId(exercise.getId());
    }
}

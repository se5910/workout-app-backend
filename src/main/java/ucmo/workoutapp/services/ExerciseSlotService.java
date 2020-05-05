package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
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

    public Iterable<ExerciseSlot> getAllExerciseSlotsByTemplateId(Long templateId, String username){
        Template template = templateRepository.getById(templateId);

        return template.getExerciseSlots();
    }

    public ExerciseSlot getExerciseSlotById(Long exerciseSlotId, String username){
        return exerciseSlotRepository.getById(exerciseSlotId);

    }

    public void deleteExerciseSlotById(Long exerciseSlotId, String username){
        exerciseSlotRepository.delete(getExerciseSlotById(exerciseSlotId, username));

    }

    // Returns exercise slot because we are actually changing the exercise slot by adding an exercise to it
    public ExerciseSlot createExerciseForExerciseSlot(Long exericseSlotId, Long exerciseId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exericseSlotId);

        exerciseSlot.setExerciseId(exerciseId);

        return exerciseSlotRepository.save(exerciseSlot);
    }

    // Get the exercise from the exercise slot by id
    public Exercise getExerciseFromExerciseSlotById(Long exerciseSlotId, String username){
        return exerciseRepository.getById(exerciseSlotRepository.getById(exerciseSlotId).getExerciseId());

    }

    // Set the exercise of the exercise slot to "null" to delete it
    public void deleteExerciseFromExerciseSlotById(Long exerciseSlotId, String username){
        // Get the correct exercise slot form the database
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);

        // "Delete" the exercise by setting the exercise in the slot to null
        exerciseSlot.setExerciseId(null);
    }
}

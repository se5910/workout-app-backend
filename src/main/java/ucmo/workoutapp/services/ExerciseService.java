package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.exceptions.DuplicateExerciseCreationException;
import ucmo.workoutapp.repositories.ExerciseRepository;
import ucmo.workoutapp.repositories.ExerciseSlotRepository;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseSlotRepository exerciseSlotRepository;

    public Exercise createExerciseForExerciseSlot(Exercise exercise, Long exerciseSlotId, String username){

        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);

        // Determine if there is an exercise already created, if not continue with the request, else throw an exception
        if (exerciseSlot.getExercise() == null) {
            exercise.setExerciseSlot(exerciseSlot);
        } else {
            throw new DuplicateExerciseCreationException("Exercise slot " + exerciseSlot.getId() + " already has an exercise. Please create a new slot");
        }

        return exerciseRepository.save(exercise);

    }

    public Exercise getExerciseById(Long exerciseId, Long exerciseSlotId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        return exerciseRepository.getById(exerciseId);
    }
}

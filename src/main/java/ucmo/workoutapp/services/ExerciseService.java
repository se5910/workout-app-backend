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

    public Exercise createExercise(Exercise exercise, String username){
        // Determine if there is an exercise already created, if not continue with the request, else throw an exception
        if (exerciseRepository.getByExerciseName(exercise.getExerciseName()) != null) {
            throw new DuplicateExerciseCreationException("Exercise '" + exercise.getExerciseName() + "' already exists. Please call your exercise a different name, or remove the duplicate.");
        }

        return exerciseRepository.save(exercise);

    }

    public Exercise getExerciseById(Long exerciseId, String username){
        return exerciseRepository.getById(exerciseId);

    }

    public void deleteExerciseById(Long exerciseId, String username){
        exerciseRepository.delete(getExerciseById(exerciseId, username));

    }

}

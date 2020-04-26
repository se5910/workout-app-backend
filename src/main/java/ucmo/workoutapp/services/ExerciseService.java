package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExerciseSlot;
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
        exercise.setExerciseSlot(exerciseSlot);

        return exerciseRepository.save(exercise);

    }

    public Exercise getExerciseById(Long exerciseId, Long exerciseSlotId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        return exerciseRepository.getById(exerciseId);
    }
}

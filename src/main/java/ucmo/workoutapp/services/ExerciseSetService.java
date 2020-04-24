package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExerciseSet;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.repositories.ExerciseSetRepository;
import ucmo.workoutapp.repositories.ExerciseSlotRepository;

@Service
public class ExerciseSetService {
    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private ExerciseSlotRepository exerciseSlotRepository;

    public ExerciseSet createExerciseSetForExerciseSlot(ExerciseSet exerciseSet, Long exerciseSlotId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        exerciseSet.setExerciseSlot(exerciseSlot);

        return exerciseSetRepository.save(exerciseSet);
    }

    public ExerciseSet getExerciseSetById(Long exerciseSlotId, Long exerciseSetId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        return exerciseSetRepository.getById(exerciseSetId);
    }
}

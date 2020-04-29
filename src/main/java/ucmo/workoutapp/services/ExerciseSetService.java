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

        // Get the number of sets belonging to the exercise slot
        Integer setNumber = exerciseSlot.getSets().size();

        // Increment to represent which set this is locally (separate from the ID. It will not be unique in the db).
        // e.g. if there are 0, this will now be set 1. If there are 4, this will now be set 5.
        setNumber++;

        // Set the set number to this new number
        exerciseSet.setSetNumber(setNumber);

        return exerciseSetRepository.save(exerciseSet);
    }

    public ExerciseSet getExerciseSetById(Long exerciseSlotId, Long exerciseSetId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        return exerciseSetRepository.getById(exerciseSetId);
    }
}

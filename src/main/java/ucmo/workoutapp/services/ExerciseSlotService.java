package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.repositories.*;

@Service
public class ExerciseSlotService {
    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private ExerciseSlotRepository exerciseSlotRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public ExerciseSlot createExerciseSlotForDay(ExerciseSlot exerciseSlot, Long weekId, String username){
        Week week = weekRepository.getById(weekId);
        exerciseSlot.setExercise(exerciseRepository.getById(Long.valueOf(1)));
        exerciseSlot.setWeek(week);

        return exerciseSlotRepository.save(exerciseSlot);
    }

    public ExerciseSlot getExerciseSlotById(Long exerciseSlotId, String username){
        return exerciseSlotRepository.getById(exerciseSlotId);

    }

    public void deleteExerciseSlotById(Long exerciseSlotId, String username){
        exerciseSlotRepository.delete(getExerciseSlotById(exerciseSlotId, username));

    }

    // Returns exercise slot because we are actually changing the exercise slot
    public ExerciseSlot createExerciseForExerciseSlot(Long exericseSlotId, Long exerciseId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exericseSlotId);

        exerciseSlot.setExercise(exerciseRepository.getById(exerciseId));

        return exerciseSlotRepository.save(exerciseSlot);
    }

    // Get the exercise from the exercise slot.
    public Exercise getExerciseFromExerciseSlotById(Long exerciseSlotId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);

        return exerciseSlot.getExercise();
    }

    // Set the exercise of the exercise slot to "null" to delete it
    public void deleteExerciseFromExerciseSlotById(Long exerciseSlotId, String username){
        // Get the correct exercise slot form the database
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);

        // "Delete" the exercise by setting the exercise in the slot to null
        exerciseSlot.setExercise(null);
    }
}

package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExerciseSet;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.repositories.ExerciseSetRepository;
import ucmo.workoutapp.repositories.ExerciseSlotRepository;
import ucmo.workoutapp.repositories.WeekRepository;

@Service
public class ExerciseSetService {
    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private WeekRepository weekRepository;

    public ExerciseSet createExerciseSetForWeek(ExerciseSet exerciseSet, Long weekId, String username){
        Week week = weekRepository.getById(weekId);
        exerciseSet.setWeek(week);

        // Get the number of sets belonging to the week
        Integer exerciseSetNumber = week.getExerciseSets().size();

        // Increment to represent which set this is locally (separate from the ID. It will not be unique in the db).
        // e.g. if there are 0, this will now be set 1. If there are 4, this will now be set 5.
        exerciseSetNumber++;

        // Set the ExerciseSet number to this new number
        exerciseSet.setExerciseSetNumber(exerciseSetNumber);

        return exerciseSetRepository.save(exerciseSet);

        //**************** There's probably a better way to name this ****************************//
    }

    public ExerciseSet getExerciseSetById(Long exerciseSetId, String username){
        return exerciseSetRepository.getById(exerciseSetId);

    }

    public void deleteExerciseSetById(Long exerciseSetId, String username){
        exerciseSetRepository.delete(getExerciseSetById(exerciseSetId, username));

    }
}

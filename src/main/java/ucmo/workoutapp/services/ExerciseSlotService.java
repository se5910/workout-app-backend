package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.repositories.*;

@Service
public class ExerciseSlotService {
    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private ExerciseSlotRepository exerciseSlotRepository;

    public ExerciseSlot createExerciseSlotForDay(ExerciseSlot exerciseSlot, Long weekId, String username){
        Week week = weekRepository.getById(weekId);
        exerciseSlot.setWeek(week);

        return exerciseSlotRepository.save(exerciseSlot);
    }

    public ExerciseSlot getExerciseSlotById(Long weekId, Long exerciseSlotId, String username){
        Week week = weekRepository.getById(weekId);
        return exerciseSlotRepository.getById(exerciseSlotId);
    }
}

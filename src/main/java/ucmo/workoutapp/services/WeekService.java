package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.entities.Template;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.repositories.*;

@Service
public class WeekService {
    @Autowired
    private ExerciseSlotRepository exerciseSlotRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Week createWeekForExerciseSlot(Week week, Long exerciseSlotId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        week.setExerciseSlot(exerciseSlot);
        week.setName(week.getName());

        return weekRepository.save(week);
    }

    public Week getWeekById(Long weekId, String username){

        return weekRepository.getById(weekId);
    }

    public void deleteWeekById(Long weekId, String username){
        weekRepository.delete(getWeekById(weekId, username));
    }
}

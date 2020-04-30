package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Day;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.repositories.*;

@Service
public class WeekService {
    @Autowired
    private ExercisePlanRepository exercisePlanRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Week createWeekForDay(Week week, Long dayId, String username){
        Day day = dayRepository.getById(dayId);
        week.setDay(day);
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

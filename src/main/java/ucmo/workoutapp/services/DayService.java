package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Day;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.repositories.DayRepository;
import ucmo.workoutapp.repositories.ExercisePlanRepository;
import ucmo.workoutapp.repositories.UserRepository;

import java.util.List;

@Service
public class DayService {
    @Autowired
    ExercisePlanRepository exercisePlanRepository;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    UserRepository userRepository;

    public Day addDayToExercisePlan(Long planId, Day day, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        day.setExercisePlan(exercisePlan);

        return dayRepository.save(day);

    }

    public Day getDayById(Long planId, Long dayId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        return dayRepository.getById(dayId);
    }

}

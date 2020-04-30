package ucmo.workoutapp.services;

import io.netty.handler.codec.MessageAggregationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
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

    @Autowired
    ClientRepository clientRepository;


    public Day createNewDayForExercisePlan(Day day, Long planId, String username){
        ExercisePlan exercisePlan = exercisePlanRepository.getByPlanId(planId);
        day.setExercisePlan(exercisePlan);
        day.setPhase(day.getPhase());
        day.setWorkoutType(day.getWorkoutType());
        day.setName(day.getName());

        return dayRepository.save(day);
    }

    public Day getDayById(Long dayId, String username){

        return dayRepository.getById(dayId);
    }

    public void deleteDayFromExercisePlan(Long dayId, String username){
      dayRepository.delete(getDayById(dayId, username));

    }

}

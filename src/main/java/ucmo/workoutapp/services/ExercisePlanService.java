package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.repositories.ExercisePlanRepository;

@Service
public class ExercisePlanService {
    @Autowired
    private ExercisePlanRepository exercisePlanRepository;

    public ExercisePlan SaveOrUpdateExercisePlan(ExercisePlan exercisePlan, Long client_id){
        if (exercisePlan.getPlanId() != null){
            ExercisePlan existingExercisePlan = exercisePlanRepository.getByPlanId(exercisePlan.getPlanId());
        }

        return exercisePlanRepository.save(exercisePlan);
    }
}

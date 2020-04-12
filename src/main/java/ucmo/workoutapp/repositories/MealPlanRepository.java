package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.MealPlan;

@Repository
public interface MealPlanRepository extends CrudRepository<MealPlan, Long> {
    MealPlan getByPlanId(long planId);

}

package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.ExercisePlan;

@Repository
public interface ExercisePlanRepository extends CrudRepository<ExercisePlan, Long> {
    ExercisePlan getByPlanId(long planId);

    Iterable<ExercisePlan> findAllByClient(Client client);

}

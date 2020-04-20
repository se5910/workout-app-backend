package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.ExercisePlan;

import java.util.Optional;

@Repository
public interface ExercisePlanRepository extends CrudRepository<ExercisePlan, Long> {
    ExercisePlan getByPlanId(Long Id);

    Iterable<ExercisePlan> findAllByClient(Client client);

}

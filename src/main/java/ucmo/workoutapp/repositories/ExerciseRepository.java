package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Exercise;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    Exercise getById(Long id);
}

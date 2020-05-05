package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.ExerciseSet;

@Repository
public interface ExerciseSetRepository extends CrudRepository<ExerciseSet, Long> {
    ExerciseSet getById(Long id);
}

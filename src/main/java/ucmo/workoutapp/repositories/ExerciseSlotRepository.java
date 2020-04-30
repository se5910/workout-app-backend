package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExerciseSlot;

@Repository
public interface ExerciseSlotRepository extends CrudRepository<ExerciseSlot, Long> {
    ExerciseSlot getById(Long id);
}

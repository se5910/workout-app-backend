package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Day;
import ucmo.workoutapp.entities.Week;

@Repository
public interface WeekRepository extends CrudRepository<Week, Long> {
    Week getById(Long id);
}

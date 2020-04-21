package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Day;

import java.util.List;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {
    Day getById(Long id);


}

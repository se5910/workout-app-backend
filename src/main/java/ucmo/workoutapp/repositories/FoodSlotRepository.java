package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ucmo.workoutapp.entities.FoodSlot;

public interface FoodSlotRepository extends CrudRepository<FoodSlot, Long> {
    FoodSlot getById(Long id);

}

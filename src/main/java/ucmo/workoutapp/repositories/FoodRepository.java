package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ucmo.workoutapp.entities.Food;

public interface FoodRepository extends CrudRepository<Food, Long> {
    Food getById(Long id);
}

package ucmo.workoutapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucmo.workoutapp.entities.Food;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    Food getById(Long id);
    Long deleteById(Long id);
}

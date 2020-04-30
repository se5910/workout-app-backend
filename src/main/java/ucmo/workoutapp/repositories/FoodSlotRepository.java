package ucmo.workoutapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucmo.workoutapp.entities.FoodSlot;

public interface FoodSlotRepository extends JpaRepository<FoodSlot, Integer> {
    FoodSlot getById(Long id);

    Iterable<FoodSlot> getAllByMeal_Id(Long mealId);
}

package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ucmo.workoutapp.entities.Meal;

public interface MealRepository extends CrudRepository<Meal, Long> {
    Meal getById(Long Id);
    Iterable<Meal> getAllByMealPlan(Long planId);
}

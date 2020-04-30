package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.ItemNotFoundException;
import ucmo.workoutapp.repositories.FoodRepository;

@Service
public class FoodService {
    @Autowired
    FoodRepository foodRepository;

    public Food getFoodById(Long FoodId ){
        Food food = foodRepository.getById(FoodId);
        if(food == null){
            throw new ItemNotFoundException("Food not found :(");
        }
        return foodRepository.getById(FoodId);
    }

    public Iterable<Food> findAllFood() {
        return foodRepository.findAll();
    }

    public void deleteFoodById(Long foodId){
        foodRepository.deleteById(foodId);
    }

    public Food createFood(Food food){
        return foodRepository.save(food);
    }



}

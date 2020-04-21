package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Should be breakfast, lunch, dinner, and snack only... list of names?
    @NotBlank(message = "Meal name required")
    private String name;

    // Each meal can have many foods, but each food cannot be duplicated inside a meal (hopefully lol)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "meal")
    @JsonIgnore
    private List<FoodSlot> foodSlots = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", updatable = false, nullable = false)
    @JsonIgnore
    private MealPlan mealPlan;

    public Meal(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodSlot> getFoodSlots() {
        return foodSlots;
    }

    public void setFoodSlots(List<FoodSlot> foodSlots) {
        this.foodSlots = foodSlots;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }
}

package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private List<Food> foods;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", updatable = false, nullable = false)
    private MealPlan mealPlan;

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

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}

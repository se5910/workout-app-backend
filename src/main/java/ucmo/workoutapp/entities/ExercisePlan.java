package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class ExercisePlan extends Plan {

    @NotBlank(message = "Exercise Plan name is required")
    private String name;

    // Each exercise plan has many exercises, and each exercise can be in in many exercise plans
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "exercisePlan")
    @JsonIgnore
    private List<Day> days;

    public ExercisePlan(){

    }

    public ExercisePlan(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

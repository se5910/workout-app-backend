package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Day name is required")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", updatable = false, nullable = false)
    @JsonIgnore
    private ExercisePlan exercisePlan;

    // Each day has many exercises, but each exercise cannot be duplicated inside a day (hopefully lol)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "day")
    @JsonIgnore
    private List<Week> weeks = new ArrayList<>();

    @NotBlank(message = "Workout type is required")
    private String workoutType;

    @NotBlank(message = "Workout phase is required")
    private String phase;

    public Day() {

    }

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

    public ExercisePlan getExercisePlan() {
        return exercisePlan;
    }

    public void setExercisePlan(ExercisePlan exercisePlan) {
        this.exercisePlan = exercisePlan;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}

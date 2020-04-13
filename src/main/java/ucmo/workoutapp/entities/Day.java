package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Should be day 1, day 2.... day 7 and no more. List of days?
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", updatable = false, nullable = false)
    private ExercisePlan exercisePlan;

    // Each day has many exercises, but each exercise cannot be duplicated inside a day (hopefully lol)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "day")
    @JsonIgnore
    private List<ExerciseSlot> exerciseSlots = new ArrayList<>();

    private String workoutType;

    private String phase;

    // What week it is
    //@Transient
    //private List<String> week;

    public Day() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExerciseSlot> getExerciseSlots() {
        return exerciseSlots;
    }

    public void setExerciseSlots(List<ExerciseSlot> exerciseSlots) {
        this.exerciseSlots = exerciseSlots;
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

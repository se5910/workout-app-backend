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

    private Integer week1;
    private Integer week2;
    private Integer week3;
    private Integer week4;

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

    public Integer getWeek1() {
        return week1;
    }

    public void setWeek1(Integer week1) {
        this.week1 = week1;
    }

    public Integer getWeek2() {
        return week2;
    }

    public void setWeek2(Integer week2) {
        this.week2 = week2;
    }

    public Integer getWeek3() {
        return week3;
    }

    public void setWeek3(Integer week3) {
        this.week3 = week3;
    }

    public Integer getWeek4() {
        return week4;
    }

    public void setWeek4(Integer week4) {
        this.week4 = week4;
    }
}

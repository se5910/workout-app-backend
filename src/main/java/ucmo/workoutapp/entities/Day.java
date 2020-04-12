package ucmo.workoutapp.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dayId;

    // Should be day 1, day 2.... day 7 and no more. List of days?
    private String name;

    // Each day has many exercises, but each exercise cannot be duplicated inside a day (hopefuly lol)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Exercise> exercises = new ArrayList<>();

    private String workoutType;

    private String phase;

    // What week it is
    @Transient
    private List<String> week;

    // Each entry in the list represents a set. Each value is the number of reps.
    // List[0] = 10 && List[1] = 5 -> Set 1, reps 10 && Set 2, reps 5
    @Transient
    private List<Integer> reps;

    public Day() {
    }

    public long getDayId() {
        return dayId;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
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

    public List<String> getWeek() {
        return week;
    }

    public void setWeek(List<String> week) {
        this.week = week;
    }

    public List<Integer> getReps() {
        return reps;
    }

    public void setReps(List<Integer> reps) {
        this.reps = reps;
    }
}

package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExerciseSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "exerciseSlot")
    private List<Sets> sets = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "exerciseSlot")
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", updatable = false, nullable = false)
    @JsonIgnore
    private Week week;

    public ExerciseSlot(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public List<Sets> getSets() {
        return sets;
    }

    public void setSets(List<Sets> sets) {
        this.sets = sets;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }
}

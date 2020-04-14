package ucmo.workoutapp.entities;

import javax.persistence.*;

@Entity
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_slot_id", updatable = false, nullable = false)
    private ExerciseSlot exerciseSlot;

    private Integer setNumber;

    private Integer weight;

    private Integer reps;

    private Integer rpe;

    public Set() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseSlot getExerciseSlot() {
        return exerciseSlot;
    }

    public void setExerciseSlot(ExerciseSlot exerciseSlot) {
        this.exerciseSlot = exerciseSlot;
    }

    public Integer getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getRpe() {
        return rpe;
    }

    public void setRpe(Integer rpe) {
        this.rpe = rpe;
    }
}

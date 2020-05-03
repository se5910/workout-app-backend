package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Week name cannot be blank")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_slot_id", updatable = false, nullable = false)
    @JsonIgnore
    private ExerciseSlot exerciseSlot;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "week")
    private List<ExerciseSet> exerciseSets;

    public Week() {

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

    public ExerciseSlot getExerciseSlot() {
        return exerciseSlot;
    }

    public void setExerciseSlot(ExerciseSlot exerciseSlot) {
        this.exerciseSlot = exerciseSlot;
    }

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(List<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }
}

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
    @JoinColumn(name = "day_id", updatable = false, nullable = false)
    private Day day;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "week")
    @JsonIgnore
    private List<ExerciseSlot> exerciseSlots;

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

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public List<ExerciseSlot> getExerciseSlots() {
        return exerciseSlots;
    }

    public void setExerciseSlots(List<ExerciseSlot> exerciseSlots) {
        this.exerciseSlots = exerciseSlots;
    }
}

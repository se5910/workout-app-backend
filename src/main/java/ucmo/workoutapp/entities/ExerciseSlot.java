package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class ExerciseSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "exerciseSlot")
    @JsonIgnore
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "day_id", updatable = false, nullable = false)
    private Day day;

    private Integer set1;
    private Integer set2;
    private Integer set3;
    private Integer set4;
    private Integer set5;

    private Integer pounds;
    private Double rpe;

    public ExerciseSlot(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Integer getSet1() {
        return set1;
    }

    public void setSet1(Integer set1) {
        this.set1 = set1;
    }

    public Integer getSet2() {
        return set2;
    }

    public void setSet2(Integer set2) {
        this.set2 = set2;
    }

    public Integer getSet3() {
        return set3;
    }

    public void setSet3(Integer set3) {
        this.set3 = set3;
    }

    public Integer getSet4() {
        return set4;
    }

    public void setSet4(Integer set4) {
        this.set4 = set4;
    }

    public Integer getSet5() {
        return set5;
    }

    public void setSet5(Integer set5) {
        this.set5 = set5;
    }

    public Integer getPounds() {
        return pounds;
    }

    public void setPounds(Integer pounds) {
        this.pounds = pounds;
    }

    public Double getRpe() {
        return rpe;
    }

    public void setRpe(Double rpe) {
        this.rpe = rpe;
    }
}

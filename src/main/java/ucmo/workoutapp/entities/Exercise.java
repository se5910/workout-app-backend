package ucmo.workoutapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseId;

    @NotBlank(message = "Exercise name is required")
    private String exerciseName;

    @NotBlank(message = "Muscle Group is required")
    private String muscleGroup;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image_1;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image_2;

    public Exercise() {

    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public byte[] getImage_1() {
        return image_1;
    }

    public void setImage_1(byte[] image_1) {
        this.image_1 = image_1;
    }

    public byte[] getImage_2() {
        return image_2;
    }

    public void setImage_2(byte[] image_2) {
        this.image_2 = image_2;
    }
}

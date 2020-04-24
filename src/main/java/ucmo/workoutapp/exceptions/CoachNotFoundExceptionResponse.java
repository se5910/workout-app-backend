package ucmo.workoutapp.exceptions;
// 
// John Irle
// 23 April 2020

public class CoachNotFoundExceptionResponse {

    private String CoachNotFound;

    public CoachNotFoundExceptionResponse(String planNotFound) {
        this.CoachNotFound = CoachNotFound;
    }

    public String getCoachNotFound() {
        return CoachNotFound;
    }

    public void setCoachNotFound(String coachNotFound) {
        CoachNotFound = coachNotFound;
    }
}

package ucmo.workoutapp.exceptions;

public class PlanNotFoundExceptionResponse {

    private String PlanNotFound;

    public PlanNotFoundExceptionResponse(String planNotFound) {
        PlanNotFound = planNotFound;
    }

    public String getPlanNotFound() {
        return PlanNotFound;
    }

    public void setPlanNotFound(String planNotFound) {
        PlanNotFound = planNotFound;
    }
}

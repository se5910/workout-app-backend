package ucmo.workoutapp.exceptions;


public class ClientNotFoundExceptionResponse {

    private String ClientNotFound;

    public ClientNotFoundExceptionResponse(String planNotFound) {
        ClientNotFound = ClientNotFound;
    }

    public String getClientNotFound() {
        return ClientNotFound;
    }

    public void setClientNotFound(String clientNotFound) {
        ClientNotFound = clientNotFound;
    }
}
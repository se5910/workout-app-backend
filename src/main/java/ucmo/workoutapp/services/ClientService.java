package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.ItemNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.UserRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public Client createOrUpdateClient(Client clientObject, String username) {
        User request = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(request);

        if (client != null) {
            // POSSIBLE REFACTORING. LOOK INTO COACH RESPONSIBILITY
            if (!client.getCoach().equals(request.getUsername())) {
                throw new ItemNotFoundException("You are not the coach of this client.");
            }

            //if (!client.getUser().getUsername().equals(request.getUsername())) {
            //    throw new ClientNotFoundException("This client information does not match your login information.");
            // }

            client.setAge(clientObject.getAge());
            client.setBodyFatPercentage(clientObject.getBodyFatPercentage());
            client.setGoalStatement(clientObject.getGoalStatement());
            client.setHeight(clientObject.getHeight());
            client.setRestingHeartRate(clientObject.getRestingHeartRate());
            client.setGoalWeight(clientObject.getGoalWeight());
            client.setHealthHistory(clientObject.getHealthHistory());
            client.setWeight(clientObject.getWeight());
            // client.setCoach(clientObject.getCoach());

            return clientRepository.save(client);
        }

        clientObject.setUser(request);

        return clientRepository.save(clientObject);
    }

    public Client getCurrentClient(String username) {
        User request = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(request);

        if (request == null) {
            throw new ClientNotFoundException("How about now?");
        }

        if (request.isCoach()) {
            throw new CoachNotFoundException("You are a coach not a client. Cannot get current client.");
        }

        if (client == null) {
            throw new ClientNotFoundException("Client doesn't not exist");
        }

        if (!request.getFullName().equals(client.getName())) {
            throw new ClientNotFoundException("You cannot access another client's data");
        }

        return client;
    }

    public Client getClientById(Long clientId, String username) {
        Client client = clientRepository.getById(clientId);
        User request = userRepository.findByUsername(username);

        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }

        if (!request.isCoach() && !client.getUser().getUsername().equals(request.getUsername())) {
            throw new ClientNotFoundException("You are a client and cannot access other client data.");
        }

        if (!client.getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client.");
        }

        return client;
    }

    // ------------------- COACH FUNCTIONALITIES NOW ----------------------

    public Iterable<Client> getAllClientsByCoach(String username) {
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach.");
        }

        return clientRepository.findAllByCoach(request.getUsername());

    }

    public Client approveClient(Long clientId, String username) {
        Client client = clientRepository.getById(clientId);
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. Unable to approve client.");
        }

        if (!client.getCoach().equals(request.getUsername())) {
            throw new ClientNotFoundException("You cannot approve this client. They have not requested you as the coach.");
        }

        client.setApproved(true);
        client.setCoach(username);

        clientRepository.save(client);

        return client;
    }

    public Client rejectClient(Long clientId, String username) {
        Client client = clientRepository.getById(clientId);
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. Unable to approve client.");
        }

        if (!client.getCoach().equals(request.getUsername())) {
            throw new ClientNotFoundException("You cannot approve this client. They have not requested you as the coach.");
        }

        client.setCoach(null);

        clientRepository.save(client);

        return client;
    }
}

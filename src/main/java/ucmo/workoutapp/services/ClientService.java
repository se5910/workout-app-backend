package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.UserRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public Client SaveOrUpdateClient(Client clientObject, String username){
        User user = userRepository.findByUsername(username);
        Client client = clientRepository.getByUser(user);
        if (client != null) {
            client.setAge(clientObject.getAge());
            client.setBodyFatPercentage(clientObject.getBodyFatPercentage());
            client.setGoalStatement(clientObject.getGoalStatement());
            client.setHeight(clientObject.getHeight());
            client.setRestingHeartRate(clientObject.getRestingHeartRate());
            client.setGoalWeight(clientObject.getGoalWeight());
            client.setHealthHistory(clientObject.getHealthHistory());
            client.setWeight(clientObject.getWeight());


            return clientRepository.save(client);
        } else {

            clientObject.setUser(user);
            clientObject.setCoach("irlejohn@gmail.com");
            return clientRepository.save(clientObject);
        }
    }

    public Client getClientByUser(String username) {
        try {
            User user = userRepository.findByUsername(username);

            if (user == null) {
                throw new ClientNotFoundException("How about now?");
            }

            Client client = clientRepository.getByUser(user);

            if (client == null) {
                throw new ClientNotFoundException("Client doesn't not exist");
            }

            // Need toLowerCase()
            System.out.println(user.getFullName()); //This is lowercase
            System.out.println(client.getName()); //This is not

            if (!user.getFullName().equals(client.getName())) {
                throw new ClientNotFoundException("Profile not found in your account");
            }

            return client;
        } catch (Exception e){
            throw new ClientNotFoundException("No Profile");
        }
    }

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Iterable<Client> getAllClientsByCoach(String username) {
        System.out.println(username);
        return clientRepository.findAllByCoach(username);
    }

}

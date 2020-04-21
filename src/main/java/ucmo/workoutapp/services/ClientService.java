package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.repositories.ClientRepository;
import ucmo.workoutapp.repositories.UserRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public Client SaveOrUpdateClient(Client client, String username){
        if (client.getID() != null) {
            Client existingClient = clientRepository.getById(client.getID());
        }
        User user = userRepository.findByUsername(username);
        client.setUser(user);

        return clientRepository.save(client);
    }

}

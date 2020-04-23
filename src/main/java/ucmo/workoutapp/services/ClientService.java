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

    public Client SaveOrUpdateClient(Client client, String username){
        if (client.getID() != null) {
            Client existingClient = clientRepository.getById(client.getID());

        }
        User user = userRepository.findByUsername(username);

        client.setUser(user);
        return clientRepository.save(client);
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
            //System.out.println(user.getFullName()); This is lowercase
            //System.out.println(client.getName()); This is not

            if (!user.getFullName().equals(client.getName().toLowerCase())) {
                throw new ClientNotFoundException("Profile not found in your account");
            }

            return client;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

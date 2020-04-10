package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client SaveOrUpdateClient(Client client, String username){
        if (client.getID() != null) {
            Client existingClient = clientRepository.getByID(client.getID());

        }
        return clientRepository.save(client);
    }

}

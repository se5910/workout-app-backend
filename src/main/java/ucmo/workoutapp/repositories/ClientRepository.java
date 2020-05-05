package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.User;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Client getById(Long id);

    List<Client> findAllByCoach(String coach);

    Client getByUser(User user);

}

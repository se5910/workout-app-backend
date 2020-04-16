package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.User;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Client getById(Long id);

    Client getByUser(User user);

}

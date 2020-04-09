package ucmo.workoutapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucmo.workoutapp.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User getById(Long id);
    List<User> findByRoles_Id(Integer id);
}

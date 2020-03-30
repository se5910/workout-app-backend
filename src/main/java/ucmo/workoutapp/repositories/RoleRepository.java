package ucmo.workoutapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucmo.workoutapp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}

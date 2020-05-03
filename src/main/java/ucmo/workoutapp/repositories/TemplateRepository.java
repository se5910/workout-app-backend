package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucmo.workoutapp.controllers.TemplateController;
import ucmo.workoutapp.entities.Template;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Long> {
    Template getById(Long id);


}

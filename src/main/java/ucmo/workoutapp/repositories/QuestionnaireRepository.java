package ucmo.workoutapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ucmo.workoutapp.entities.Questionnaire;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long> {
    Questionnaire getById(Long id);
    Iterable<Questionnaire> findAllByCoachUsername(String username);

}

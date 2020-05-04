package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Questionnaire;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.ItemNotFoundException;
import ucmo.workoutapp.exceptions.UsernameAlreadyExistsException;
import ucmo.workoutapp.repositories.QuestionnaireRepository;
import ucmo.workoutapp.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

@Service
public class QuestionnaireService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public Questionnaire createQuestionnaireForUser(Questionnaire questionnaire, String username){
        User user = userRepository.findByUsername(username);

        if (user.getQuestionnaire() != null){
            throw new UsernameAlreadyExistsException("You already have a questionnaire filled out.");
        }

        if (user == null){
            throw new EntityNotFoundException("Username does not exist");
        }

        if (user.isCoach()){
            throw new CoachNotFoundException("User '" + username + "' is a coach. Coach's cannot create questionnaires.");
        }

        questionnaire.setUser(user);
        user.setCoachRequested(questionnaire.getCoachUsername());

        User coach = userRepository.findByUsername(questionnaire.getCoachUsername());
        if (coach == null){
            throw new CoachNotFoundException("Coach '" + questionnaire.getCoachUsername() + "' does not exist");
        }

        if (!coach.isCoach()){
            throw new CoachNotFoundException(("User '" + coach.getUsername() + "' is not a coach."));
        }

        return questionnaireRepository.save(questionnaire);
    }

    public Questionnaire getQuestionnaireOfUser(Long userId, String username){
        User currentUser = userRepository.findByUsername(username);
        User user = userRepository.getById(userId);

        if (user == null){
            throw new NullPointerException("User Id does not exist");
        }

        if (!currentUser.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot access this");
        }

        if (user.getQuestionnaire() == null){
            throw new ItemNotFoundException("There is no questionnaire for user '" + username + "'.");
        }

        return user.getQuestionnaire();
    }

    public Iterable<Questionnaire> getAllQuestionnairesOfCoach(String username){
        if (!userRepository.findByUsername(username).isCoach()){
            throw new CoachNotFoundException("You are not a coach. You cannot look for people's questionnaires");
        }
        return questionnaireRepository.findAllByCoachUsername(username);
    }

    public Questionnaire getQuestionnaireById(Long questionnaireId, String username){
        return questionnaireRepository.getById(questionnaireId);
    }
}

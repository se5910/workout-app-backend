package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.*;
import ucmo.workoutapp.exceptions.ClientNotFoundException;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.*;

import javax.persistence.EntityNotFoundException;

@Service
public class ExerciseSetService {
    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ExerciseSet createOrUpdateExerciseForSet(ExerciseSet exerciseSet, Long weekId, String username){
        Week week = weekRepository.getById(weekId);
        User request = userRepository.findByUsername(username);

        if (exerciseSet == null) {
            throw new EntityNotFoundException("Exercise Set is null");
        }

        if (week == null) {
            throw new EntityNotFoundException("Week is null");
        }

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are no a coach you cannot create or update a week");
        }

        if (request.isCoach() && !week.getExerciseSlot().getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())) {
            throw new ClientNotFoundException("You are not a coach and you are not this client. Unable to make changes to 'Set'.");
        }

        if (exerciseSet.getId() != null) {
            ExerciseSet existingExerciseSet = exerciseSetRepository.getById(exerciseSet.getId());

            existingExerciseSet.setExerciseSetNumber(exerciseSet.getExerciseSetNumber());
            existingExerciseSet.setWeek(exerciseSet.getWeek());
            existingExerciseSet.setReps(exerciseSet.getReps());
            existingExerciseSet.setRpe(exerciseSet.getRpe());
            existingExerciseSet.setWeight(exerciseSet.getWeight());

            return exerciseSetRepository.save(existingExerciseSet);
        }

        exerciseSet.setWeek(week);

        // Get the number of sets belonging to the week
        Integer exerciseSetNumber = week.getExerciseSets().size();

        // Increment to represent which set this is locally (separate from the ID. It will not be unique in the db).
        // e.g. if there are 0, this will now be set 1. If there are 4, this will now be set 5.
        exerciseSetNumber++;

        // Set the ExerciseSet number to this new number
        exerciseSet.setExerciseSetNumber(exerciseSetNumber);

        return exerciseSetRepository.save(exerciseSet);

        //**************** There's probably a better way to name this ****************************//
    }

    public Iterable<ExerciseSet> getAllExerciseSetsForWeek(Long weekId, String username) {
        Week week = weekRepository.getById(weekId);
        User request = userRepository.findByUsername(username);

        if (week == null) {
            throw new PlanNotFoundException("Template does not exist");
        }

        if (request.isCoach() && !week.getExerciseSlot().getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !week.getExerciseSlot().getTemplate().getExercisePlan().getClient().equals(clientRepository.getByUser(request))) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return week.getExerciseSets();

    }

    public ExerciseSet getExerciseSetById(Long exerciseSetId, String username){
        User request = userRepository.findByUsername(username);
        ExerciseSet exerciseSet = exerciseSetRepository.getById(exerciseSetId);

        if (exerciseSet == null) {
            throw new EntityNotFoundException("Week not found");
        }

        if (request.isCoach() && !exerciseSet.getWeek().getExerciseSlot().getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !exerciseSet.getWeek().getExerciseSlot().getTemplate().getExercisePlan().getClient().equals(clientRepository.getByUser(request))) {
            throw new CoachNotFoundException("Exercise Plan not found in your account");
        }

        return exerciseSet;

    }

    public void deleteExerciseSetById(Long exerciseSetId, String username){
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot delete a plan");
        }

        // Utilize getExerciseSetById checks to insure coach username matches client username from plan
        ExerciseSet exerciseSet = getExerciseSetById(exerciseSetId, username);

        exerciseSetRepository.delete(exerciseSet);

    }
}

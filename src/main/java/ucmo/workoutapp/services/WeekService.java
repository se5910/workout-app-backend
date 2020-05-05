package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.ExerciseSlot;
import ucmo.workoutapp.entities.Template;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.exceptions.CoachNotFoundException;
import ucmo.workoutapp.exceptions.PlanNotFoundException;
import ucmo.workoutapp.repositories.*;

import javax.persistence.EntityNotFoundException;

@Service
public class WeekService {
    @Autowired
    private ExerciseSlotRepository exerciseSlotRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;


    public Week createWeekForExerciseSlot(Week week, Long exerciseSlotId, String username){
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        User request = userRepository.findByUsername(username);

        if (week == null) {
            throw new EntityNotFoundException("Week is null");
        }

        if (exerciseSlot == null) {
            throw new EntityNotFoundException("Exercise Slot is null");
        }

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are no a coach you cannot create or update a week");
        }

        if (request.isCoach() && !week.getExerciseSlot().getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (week.getId() != null) {
            Week existingWeek = weekRepository.getById(week.getId());

            existingWeek.setWeek(week.getWeek());
            existingWeek.setExerciseSets(week.getExerciseSets());
            existingWeek.setExerciseSlot(week.getExerciseSlot());

            return weekRepository.save(existingWeek);
        }

        // Get the number of weeks belonging to the set
        week.setExerciseSlot(exerciseSlot);

        // Increment to represent which week this is locally (separate from the ID. It will not be unique in the db).
        // e.g. if there are 0, this will now be set 1. If there are 4, this will now be set 5.
        Integer weekNumber = exerciseSlot.getWeeks().size();

        // Set the ExerciseSet number to this new number
        weekNumber++;

        week.setWeek(weekNumber);

        return weekRepository.save(week);
    }

    public Week getWeekById(Long weekId, String username){
        User request = userRepository.findByUsername(username);
        Week week = weekRepository.getById(weekId);

        if (week == null) {
            throw new EntityNotFoundException("Week not found");
        }

        if (request.isCoach() && !week.getExerciseSlot().getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())){
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !week.getExerciseSlot().getTemplate().getExercisePlan().getClient().equals(clientRepository.getByUser(request))) {
            throw new CoachNotFoundException("Exercise Plan not found in your account");
        }

        return week;
    }

    public void deleteWeekById(Long weekId, String username){
        User request = userRepository.findByUsername(username);

        if (!request.isCoach()) {
            throw new CoachNotFoundException("You are not a coach. You cannot delete a plan");
        }

        // Utilize getWeekById checks to insure coach username matches client username from plan
        Week week = getWeekById(weekId, username);

        weekRepository.delete(week);
    }

    public Iterable<Week> getAllWeeksByExerciseSlotId(Long exerciseSlotId, String username) {
        ExerciseSlot exerciseSlot = exerciseSlotRepository.getById(exerciseSlotId);
        User request = userRepository.findByUsername(username);

        if (exerciseSlot == null) {
            throw new PlanNotFoundException("Template does not exist");
        }

        if (request.isCoach() && !exerciseSlot.getTemplate().getExercisePlan().getClient().getCoach().equals(request.getUsername())) {
            throw new CoachNotFoundException("You are not the coach of this client");
        }

        if (!request.isCoach() && !exerciseSlot.getTemplate().getExercisePlan().getClient().equals(clientRepository.getByUser(request))) {
            throw new PlanNotFoundException("Exercise Plan not found in your account");
        }

        return exerciseSlot.getWeeks();
    }
}

package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.SpringCacheAnnotationParser;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Day;
import ucmo.workoutapp.entities.Exercise;
import ucmo.workoutapp.entities.ExercisePlan;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.services.DayService;
import ucmo.workoutapp.services.ExercisePlanService;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercise")
public class ExercisePlanController {
    @Autowired
    private ExercisePlanService exercisePlanService;

    @Autowired
    private DayService dayService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewExercisePlan(@Valid @RequestBody ExercisePlan exercisePlan, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        exercisePlanService.SaveOrUpdateExercisePlan(exercisePlan, principal.getName());
        return new ResponseEntity<>(exercisePlan, HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public  Iterable<ExercisePlan> getAllExercisePlans(Principal principal) {

        return exercisePlanService.findAllExercisePlans(principal.getName());
    }
    /*
     api/exercise/1234
     export const getPlan = (id, history) => async dispatch => {
        try {
            await axios.get(`/api/exercise/${id}`)
            .then(res =>
                dispatch({
                            type: GET_PROJECT,
                    payload: res.data
        }))
        } catch (err) {
            history.push('/dashboard')
        }
    } */
    @GetMapping("/{planId}")
    public ResponseEntity<?> getPlanById(@PathVariable Long planId, Principal principal) {
        ExercisePlan plan = exercisePlanService.findExercisePlanById(planId, principal.getName());

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable Long planId, Principal principal) {
        exercisePlanService.deleteByExercisePlanId(planId, principal.getName());

        return new ResponseEntity<>("Plan with ID: '" + planId + "' was deleted.", HttpStatus.OK);
    }

    @PostMapping("/{planId}/day")
    public ResponseEntity<?> addDayToExercisePlan(@PathVariable Long planId, Principal principal) {
        Day day = new Day();
        Day test = dayService.addDayToExercisePlan(planId, day, principal.getName());

        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @GetMapping("/{planId}/{dayId}")
    public ResponseEntity<?> getAllDaysFromExercisePlan(@PathVariable Long planId, @PathVariable Long dayId, Principal principal){
        Day day = dayService.getDayById(planId, dayId, principal.getName());

        return new ResponseEntity<>(day, HttpStatus.OK);
    }
}

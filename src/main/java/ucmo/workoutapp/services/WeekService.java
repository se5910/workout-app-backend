package ucmo.workoutapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucmo.workoutapp.entities.Template;
import ucmo.workoutapp.entities.Week;
import ucmo.workoutapp.repositories.*;

@Service
public class WeekService {
    @Autowired
    private ExercisePlanRepository exercisePlanRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Week createWeekForTemplate(Week week, Long templateId, String username){
        Template template = templateRepository.getById(templateId);
        week.setTemplate(template);
        week.setName(week.getName());

        return weekRepository.save(week);
    }

    public Week getWeekById(Long weekId, String username){

        return weekRepository.getById(weekId);
    }

    public void deleteWeekById(Long weekId, String username){
        weekRepository.delete(getWeekById(weekId, username));
    }
}

package tn.esprit.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Dto.emploiDuTemps.PlanningDTO;
import tn.esprit.spring.repositories.SeanceclasseRepository;
import tn.esprit.spring.services.PlanningServiceImp;
import tn.esprit.spring.services.SeanceclasseServicesImpl;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/api/planning")
@AllArgsConstructor
public class PlanningController {

    @Autowired
    private PlanningServiceImp planningService;
    @Autowired
    private SeanceclasseRepository seanceclasseRepository;
    @Autowired
    private SeanceclasseServicesImpl seanceclasseServicesImpl;


    @PostMapping("/generate")
    public String generateSchedule(@RequestBody PlanningDTO request) {
        try {
            LocalDate startDate = request.getStartDate();
            LocalTime startTime = request.getStartTime();
            LocalTime endTime = request.getEndTime();
            Integer duration = request.getSessionDuration();

            PlanningServiceImp.START_TIME = startTime;
            PlanningServiceImp.END_TIME_WEEKDAY = endTime;
            PlanningServiceImp.DURATION = duration;

            planningService.generateSchedule(startDate);


            return "Schedule generated successfully starting from " + startDate;
        } catch (Exception e) {
            return "Error generating schedule: " + e.getMessage();
        }
    }

    @GetMapping()
    public List<PlanningServiceImp.MonthlyHours> getMonthlyTeachingTime() {
        return planningService.getMonthlyTeachingTime();
    }
}


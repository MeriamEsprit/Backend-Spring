package tn.esprit.spring.Dto.emploiDuTemps;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Data
public class PlanningDTO {
    private LocalDate startDate;
    private int sessionDuration;
    private LocalTime startTime;
    private LocalTime endTime;
}

package tn.esprit.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.Dto.emploiDuTemps.*;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



@Service
public class PlanningServiceImp implements IPlanningService {

    private static final Logger logger = LoggerFactory.getLogger(PlanningServiceImp.class);

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private UtilisateurRepository enseignantRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private SeanceclasseRepository seanceClasseRepository;

    @Autowired
    private JourFerieRepository jourFerieRepository;

    @Autowired
    private HeureDejeunerRepository heureDejeunerRepository;

    @Autowired
    private SeanceclasseServicesImpl seanceclasseServices;

    @Autowired
    private UserService userService;

    public static LocalTime START_TIME = LocalTime.of(9, 0);
    public static LocalTime END_TIME_WEEKDAY = LocalTime.of(18, 0);
    public static Integer DURATION = 0;
    private static LocalTime END_TIME_SATURDAY = LocalTime.of(13, 0);

    @Override
    public void generateSchedule(LocalDate startDate) {


        List<Classe> classes = classeRepository.findAll();
        Map<Long, Long> classRoomAssignments = assignRoomsToClasses(classes);
        Map<Long, Map<Long, Utilisateur>> classTeacherAssignments = assignTeachersToClasses(classes);

        for (Classe classe : classes) {
            Long salleId = classRoomAssignments.get(classe.getId());
            for (Matiere matiere : classe.getMatieres()) {
                int heuresRestantes = matiere.getNbreHeures();
                Utilisateur enseignant = classTeacherAssignments.get(classe.getId()).get(matiere.getId());

                if (enseignant == null) {
                    throw new RuntimeException("No teacher found with required competence for class " + classe.getNomClasse() + " and subject " + matiere.getNomMatiere());
                }

                while (heuresRestantes > 0) {
                    SeanceClasseDTO seanceDTO = createSeanceDTO(classe, matiere, enseignant, salleId, startDate);
                    SeanceClasse seance = seanceclasseServices.addSeanceClasse(seanceDTO);
                    LocalDateTime heureDebut = LocalDateTime.ofInstant(seance.getHeureDebut(), ZoneId.systemDefault());
                    LocalDateTime heureFin = LocalDateTime.ofInstant(seance.getHeureFin(), ZoneId.systemDefault());
                    heuresRestantes -= Duration.between(heureDebut, heureFin).toHours();
                }
            }
        }
    }

    private Map<Long, Long> assignRoomsToClasses(List<Classe> classes) {
        List<Salle> salles = salleRepository.findAll();
        Map<Long, Long> classRoomAssignments = new HashMap<>();
        int salleIndex = 0;

        for (Classe classe : classes) {
            Long salleId = salles.get(salleIndex).getIdSalle();
            classRoomAssignments.put(classe.getId(), salleId);
            salleIndex = (salleIndex + 1) % salles.size();
        }

        return classRoomAssignments;
    }

    private Map<Long, Map<Long, Utilisateur>> assignTeachersToClasses(List<Classe> classes) {
        Map<Long, Map<Long, Utilisateur>> classTeacherAssignments = new HashMap<>();

        for (Classe classe : classes) {
            Map<Long, Utilisateur> teacherAssignments = new HashMap<>();
            for (Matiere matiere : classe.getMatieres()) {
                List<Utilisateur> enseignants = enseignantRepository.findByCompetence(matiere.getCompetence());
                if (!enseignants.isEmpty()) {
                    teacherAssignments.put(matiere.getId(), enseignants.get(0));
                }
            }
            classTeacherAssignments.put(classe.getId(), teacherAssignments);
        }

        return classTeacherAssignments;
    }

    @Override
    public SeanceClasseDTO createSeanceDTO(Classe classe, Matiere matiere, Utilisateur enseignant, Long salleId, LocalDate startDate) {
        SeanceClasseDTO seanceDTO = new SeanceClasseDTO();
        seanceDTO.setClasse(new ClasseDTO(classe.getId()));
        seanceDTO.setMatiere(new MatiereDTO(matiere.getId()));
        seanceDTO.setEnseignant(new EnseignantDTO(enseignant.getId()));
        seanceDTO.setSalle(new SalleDTO(salleId));

        LocalDateTime nextAvailableTime = findNextAvailableTime(startDate, classe, enseignant);
        seanceDTO.setHeureDebut(nextAvailableTime.atZone(ZoneId.systemDefault()).toInstant());
        seanceDTO.setHeureFin(nextAvailableTime.plusHours(DURATION).atZone(ZoneId.systemDefault()).toInstant());

        return seanceDTO;
    }

    @Override
    public LocalDateTime findNextAvailableTime(LocalDate startDate, Classe classe, Utilisateur enseignant) {
        LocalDateTime dateTime = startDate.atTime(START_TIME);

        while (true) {
            final LocalDateTime finalDateTime = dateTime;
            boolean isHoliday = jourFerieRepository.findByDateBetween(finalDateTime.toLocalDate(), finalDateTime.toLocalDate()).size() > 0;

            List<HeureDejeuner> lunchHours = heureDejeunerRepository.findAll();
            boolean isLunchTime = lunchHours.stream()
                    .anyMatch(lunch -> (finalDateTime.toLocalTime().isBefore(LocalTime.of(lunch.getAuHeure(), 0)) &&
                            finalDateTime.plusHours(DURATION).toLocalTime().isAfter(LocalTime.of(lunch.getDuHeure(), 0))));

            boolean isSunday = finalDateTime.getDayOfWeek() == DayOfWeek.SUNDAY;
            boolean isBeyondHours = finalDateTime.toLocalTime().isAfter(finalDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ? END_TIME_SATURDAY : END_TIME_WEEKDAY) ||
                    finalDateTime.plusHours(DURATION).toLocalTime().isAfter(finalDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ? END_TIME_SATURDAY : END_TIME_WEEKDAY);

            // Check if the teacher has already reached the maximum sessions per day
            List<SeanceClasse> seances = seanceClasseRepository.findByDate(finalDateTime.toLocalDate());
            long sessionsToday = seances.stream()
                    .filter(seance -> seance.getEnseignant().getId().equals(enseignant.getId()))
                    .count();

            int maxSessionsPerDay = 4; // Adjust as needed

            if (!isHoliday && !isLunchTime && !isSunday && !isBeyondHours && sessionsToday < maxSessionsPerDay) {
                List<SeanceClasse> existingConflicts = seanceClasseRepository.findByHeureDebut(finalDateTime.atZone(ZoneId.systemDefault()).toInstant());
                boolean conflict = existingConflicts.stream()
                        .anyMatch(seance -> seance.getClasse().getId().equals(classe.getId()) ||
                                seance.getEnseignant().getId().equals(enseignant.getId()));

                if (!conflict) {
                    break;
                }
            }

            dateTime = dateTime.plusHours(4);
            if (dateTime.toLocalTime().isAfter(finalDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ? END_TIME_SATURDAY : END_TIME_WEEKDAY)) {
                dateTime = dateTime.plusDays(1).withHour(START_TIME.getHour());
            }
        }

        return dateTime;
    }

    public interface MonthlyHours {

        Integer getYear();

        Integer getMonth();

        Long getTotalHours();
    }

    @Override
    public Long findAvailableSalle(LocalDate date) {
        List<Salle> salles = salleRepository.findAll();
        for (Salle salle : salles) {
            List<SeanceClasse> seances = seanceClasseRepository.findByDate(date);
            boolean isAvailable = seances.stream()
                    .noneMatch(seance -> seance.getSalle().getIdSalle().equals(salle.getIdSalle()));
            if (isAvailable) {
                return salle.getIdSalle();
            }
        }
        return null;
    }

    @Override
    public List<MonthlyHours> getMonthlyTeachingTime() {

        Utilisateur utilisateur = this.userService.getInfo();

        if (utilisateur.getRole() == ERole.ROLE_STUDENT) {
            Classe ccc = userService.getClasseByUserId(utilisateur.getId());

            System.out.println(ccc);
            System.out.println(utilisateur);
            return seanceClasseRepository.findMonthlyTeachingTimeByClass(ccc.getId());
        } else if (utilisateur.getRole() == ERole.ROLE_TEACHER) {
            return seanceClasseRepository.findMonthlyTeachingTime(utilisateur.getId());
        } else
            return null;

    }
}

package tn.esprit.spring.controllers;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Utilisateur;
import tn.esprit.spring.services.UserService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    @Autowired
    private UserService utilisateurService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboardAdmin() {
        try {
            List<Utilisateur> students = utilisateurService.getAllUserByRole(ERole.ROLE_STUDENT);
            List<Utilisateur> teachers = utilisateurService.getAllUserByRole(ERole.ROLE_TEACHER);

            if (students.isEmpty() && teachers.isEmpty()) {
                return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
            }

            Map<String, Object> response = new HashMap<>();

            // Calculate percentages for students
            if (!students.isEmpty()) {
                long totalStudents = students.size();
                Map<String, String> studentGenderPercentages = calculatePercentages(students, Utilisateur::getGender, totalStudents);
                Map<String, String> studentDobPercentagesByYear = calculateDobPercentagesByYear(students, totalStudents);
                Map<String, String> studentStartEducationPercentagesByStartEducation = calculateStartEducationPercentages(students, totalStudents);

                response.put("totalStudents", totalStudents);
                response.put("studentGenderPercentages", studentGenderPercentages);
                response.put("studentDobPercentagesByYear", studentDobPercentagesByYear);
                response.put("studentStartEducationPercentagesByStartEducation", studentStartEducationPercentagesByStartEducation);
            }

            // Calculate percentages for teachers
            if (!teachers.isEmpty()) {
                long totalTeachers = teachers.size();
                Map<String, String> teacherGenderPercentages = calculatePercentages(teachers, Utilisateur::getGender, totalTeachers);
                Map<String, String> teacherDobPercentagesByYear = calculateDobPercentagesByYear(teachers, totalTeachers);

                response.put("totalTeachers", totalTeachers);
                response.put("teacherGenderPercentages", teacherGenderPercentages);
                response.put("teacherDobPercentagesByYear", teacherDobPercentagesByYear);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, String> calculatePercentages(List<Utilisateur> utilisateurs, java.util.function.Function<Utilisateur, String> classifier, long total) {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Long> counts = utilisateurs.stream()
                .collect(Collectors.groupingBy(classifier, Collectors.counting()));

        return counts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> df.format((e.getValue() * 100.0) / total)));
    }

    private Map<String, String> calculateDobPercentagesByYear(List<Utilisateur> utilisateurs, long total) {
        DecimalFormat df = new DecimalFormat("0.00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Long> yearCounts = utilisateurs.stream()
                .map(Utilisateur::getDateofbirth)
                .map(date -> LocalDate.parse(date, formatter).getYear())
                .collect(Collectors.groupingBy(year -> String.valueOf(year), Collectors.counting()));

        return yearCounts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> df.format((e.getValue() * 100.0) / total)));
    }

    private Map<String, String> calculateStartEducationPercentages(List<Utilisateur> utilisateurs, long total) {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Long> startEducationCounts = utilisateurs.stream()
                .collect(Collectors.groupingBy(Utilisateur::getStarteducation, Collectors.counting()));

        return startEducationCounts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> df.format((e.getValue() * 100.0) / total)));
    }
}

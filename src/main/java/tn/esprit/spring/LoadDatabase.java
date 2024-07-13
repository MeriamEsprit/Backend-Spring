package tn.esprit.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoadDatabase {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    ModuleRepository moduleRepository;

    @Bean
    CommandLineRunner initDatabase(UtilisateurRepository userRepository, ClasseRepository classeRepository, SalleRepository salleRepository, SeanceclasseRepository seanceclasseRepository, MatiereRepository matiereRepository){
        return args -> {
            // Vérifier et ajouter les classes
            if (classeRepository.count() == 0) {
                List<String> classes = new ArrayList<>();
                classes.add("1CINFO1");
                classes.add("1CINFO2");
                classes.add("2CINFO1");
                classes.add("2CINFO2");
                classes.add("3CINFO1");
                classes.add("3CINFO2");
                classes.add("4CINFO1");
                classes.add("4CINFO2");

                int index = 0;

                for (String c : classes) {
                    index += 1;
                    Classe classe = new Classe();
                    classe.setNomClasse(c);
                    classeRepository.save(classe);

                    for (int i = 0; i < 10; i++) {
                        String email = i + "student" + c + "@esprit.tn";
                        if (!userRepository.existsByEmail(email)) {
                            Utilisateur student = new Utilisateur();
                            student.setEmail(email);
                            student.setCin("0" + (9868476 + index + i));
                            student.setIdentifiant("224SMT00" + (index + i));
                            student.setNom("student");
                            student.setPrenom("S" + index);
                            student.setRole(ERole.ROLE_STUDENT);
                            student.setClasse(classe);
                            student.setMotDePasse(encoder.encode("0000"));
                            userRepository.save(student);
                            System.out.println(student.getEmail());
                            index += 1;
                        }
                    }
                }
            }

            // Vérifier et ajouter les enseignants
            if (userRepository.countByRole(ERole.ROLE_TEACHER) == 0) {
                int index = 0;
                for (int i = 0; i < 10; i++) {
                    String email = i + "teacher@esprit.tn";
                    if (!userRepository.existsByEmail(email)) {
                        Utilisateur teacher = new Utilisateur();
                        teacher.setEmail(email);
                        teacher.setCin("0" + (9868476 + index + i));
                        teacher.setNom("teacher");
                        teacher.setPrenom("T" + index);
                        teacher.setRole(ERole.ROLE_TEACHER);
                        teacher.setMotDePasse(encoder.encode("0000"));
                        userRepository.save(teacher);
                        System.out.println(teacher.getEmail());
                        index += 1;
                    }
                }
            }

            // Vérifier et ajouter l'admin
            if (!userRepository.existsByEmail("admin@esprit.tn")) {
                Utilisateur admin = new Utilisateur();
                admin.setEmail("admin@esprit.tn");
                admin.setRole(ERole.ROLE_ADMIN);
                admin.setMotDePasse(encoder.encode("0000"));
                userRepository.save(admin);
                System.out.println(admin.getEmail());
            }

            // Vérifier et ajouter les salles
            if (salleRepository.count() == 0) {
                Salle salleA = new Salle();
                salleA.setNom_salle("Salle A");
                salleA.setCapacite(30);
                salleRepository.save(salleA);

                Salle salleB = new Salle();
                salleB.setNom_salle("Salle B");
                salleB.setCapacite(25);
                salleRepository.save(salleB);

                Salle salleC = new Salle();
                salleC.setNom_salle("Salle C");
                salleC.setCapacite(20);
                salleRepository.save(salleC);
            }

            // Vérifier et ajouter les matières
            if (moduleRepository.count() == 0 && matiereRepository.count() == 0) {
                Module module = new Module();
                module.setNom("Informatique");
                module.setDescription("Informatique Esprit");
                moduleRepository.save(module);

                List<String> matieres = new ArrayList<>();
                matieres.add("Devops");
                matieres.add("Angular");
                matieres.add("Spring");

                for (String m : matieres) {
                    Matiere matiere = new Matiere();
                    matiere.setModule(module);
                    matiere.setNomMatiere(m);
                    matiere.setNbreHeures(40);
                    matiere.setCoefficientTP(0.2);
                    matiere.setCoefficientCC(0.3);
                    matiere.setCoefficientExamen(0.5);
                    matiereRepository.save(matiere);
                }
            }
        };
    }
}

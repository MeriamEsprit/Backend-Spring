package tn.esprit.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.repositories.*;


import java.time.Instant;
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
                if (!classeRepository.existsBynomClasse(c)) {

                    Classe classe = new Classe();
                    classe.setNomClasse(c);
                    classeRepository.save(classe);
                    for (int i = 0; i < 10; i++) {
                        if (!userRepository.existsByEmail(i+"student" + c + "@esprit.tn")) {
                            Utilisateur student = new Utilisateur();
                            student.setEmail(i+"student" + c + "@esprit.tn");
                            student.setCin("0"+(9868476+index+i));
                            student.setIdentifiant("224SMT00"+(index+i));
                            student.setNom("student");
                            student.setPrenom("S"+index);
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

            for (int i = 0; i < 10; i++) {
                if (!userRepository.existsByEmail(i+"teacher@esprit.tn")) {
                    Utilisateur student = new Utilisateur();
                    student.setEmail(i+"teacher@esprit.tn");
                    student.setCin("0"+(9868476+index+i));
                    student.setNom("teacher");
                    student.setPrenom("T"+index);
                    student.setRole(ERole.ROLE_TEACHER);
                    student.setMotDePasse(encoder.encode("0000"));
                    userRepository.save(student);
                    System.out.println(student.getEmail());
                    index += 1;
                }
            }

            if(!userRepository.existsByEmail("admin@esprit.tn")){
                Utilisateur admin = new Utilisateur();
                admin.setEmail("admin@esprit.tn");
                admin.setRole(ERole.ROLE_ADMIN);
                admin.setMotDePasse(encoder.encode("0000"));
                userRepository.save(admin);
                System.out.println(admin.getEmail());
            }

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

            // Adding Matieres
//            List<String> matieres = new ArrayList<>();
//            Module module = new Module();
//            module.setNom("Informatique");
//            module.setDescription("Informatique Esprit");
//            moduleRepository.save(module);
//            matieres.add("Devops");
//            matieres.add("Angular");
//            matieres.add("Spring");
//            for (String m : matieres) {
//                Matiere matiere = new Matiere();
//                matiere.setModule(module);
//                matiere.setNomMatiere(m);
//                matiere.setNbreHeures(40);
//                matiere.setCoefficientTP(0.2);
//                matiere.setCoefficientCC(0.3);
//                matiere.setCoefficientExamen(0.5);
//                matiereRepository.save(matiere);
//            }

            // Adding SeanceClasse
            List<SeanceClasse> seanceClasses = new ArrayList<>();
            for (Classe classe : classeRepository.findAll()) {
                for (Matiere matiere : matiereRepository.findAll()) {
                    for (Salle salle : salleRepository.findAll()) {
                        for (Utilisateur enseignant : userRepository.findAllByRole(ERole.ROLE_TEACHER)) {
                            SeanceClasse seanceClasse = new SeanceClasse();
                            seanceClasse.setHeureDebut(Instant.now());
                            seanceClasse.setHeureFin(Instant.now().plusSeconds(3600));
                            seanceClasse.setClasse(classe);
                            seanceClasse.setMatiere(matiere);
                            seanceClasse.setSalle(salle);
                            seanceClasse.setEnseignant(enseignant);
                            seanceclasseRepository.save(seanceClasse);
                        }
                    }
                }
            }




        };
    }
}
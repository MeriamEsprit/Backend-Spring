package tn.esprit.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.repositories.UtilisateurRepository;

import java.util.Arrays;

@EnableScheduling
@SpringBootApplication
public class Application {

	private PasswordEncoder encoder;
	private UtilisateurRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	CommandLineRunner init(UtilisateurRepository userRepo) {
//		return args -> {
//
//			//  add create user in each role
//			userRepo.findByEmail("etudiant@esprit.com").orElseGet(() -> {
//				Utilisateur user = new Utilisateur("etudiant@esprit.com", encoder.encode("00000"));
//				user.setRole(ERole.ROLE_STUDENT);
//				return userRepository.save(user);
//			});
//
//			userRepo.findByEmail("enseignant@esprit.com").orElseGet(() -> {
//				Utilisateur user = new Utilisateur("enseignant@esprit.com", encoder.encode("00000"));
//				user.setRole(ERole.ROLE_TEACHER);
//				return userRepository.save(user);
//			});
//
//			userRepo.findByEmail("admin@esprit.com").orElseGet(() -> {
//				Utilisateur user = new Utilisateur("admin@esprit.com", encoder.encode("00000"));
//				user.setRole(ERole.ROLE_ADMIN);
//				return userRepository.save(user);
//			});
//
//		};
//	}

}

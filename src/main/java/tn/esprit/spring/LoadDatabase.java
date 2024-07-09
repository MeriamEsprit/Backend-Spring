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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class LoadDatabase {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    CompetenceRepository competenceRepository;
    @Bean
    CommandLineRunner initDatabase(UtilisateurRepository userRepository, ClasseRepository classeRepository, SalleRepository salleRepository, SeanceclasseRepository seanceclasseRepository, MatiereRepository matiereRepository, BadWordRepository badWordRepository){
        return args -> {

            if(!userRepository.existsByEmail("admin@esprit.tn")){
                Utilisateur admin = new Utilisateur();
                admin.setEmail("admin@esprit.tn");
                admin.setRole(ERole.ROLE_ADMIN);
                admin.setMotDePasse(encoder.encode("0000"));
                userRepository.save(admin);
                System.out.println(admin.getEmail());
            }

            List<String> classes = new ArrayList<>();
            classes.add("1CINFO1");
            classes.add("1CINFO2");
            classes.add("1CINFO3");
            classes.add("1CINFO4");
            classes.add("2CINFO1");
            classes.add("2CINFO2");
            classes.add("2CINFO3");
            classes.add("2CINFO4");
            classes.add("3CINFO1");
            classes.add("3CINFO2");
            classes.add("3CINFO3");
            classes.add("3CINFO4");
            classes.add("4CINFO1");
            classes.add("4CINFO2");
            classes.add("4CINFO3");
            classes.add("4CINFO4");

            int index = 0;

            for (String c : classes) {
                index += 1;
                if (!classeRepository.existsBynomClasse(c)) {

                    Classe classe = new Classe();
                    classe.setNomClasse(c);
                    classeRepository.save(classe);
                    for (int i = 0; i < 40; i++) {
                        if (!userRepository.existsByEmail(i+"student" + c + "@esprit.tn")) {
                            Utilisateur student = new Utilisateur();
                            student.setEmail(i+"student" + c + "@esprit.tn");
                            student.setCin("0"+(9868476+index+i));
                            student.setIdentifiant("224SMT00"+(index+i));
                            student.setNom("student");
                            student.setPrenom("S"+index);
                            student.setGender(this.generateRandomGender());
                            student.setDateofbirth(this.generateRandomDateOfBirth());
                            student.setStarteducation(this.generateRandomYear());
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
            List<String> competences = new ArrayList<>();
            competences.add("France");
            competences.add("English");
            competences.add("Mathématiques");
            competences.add("Réseaux");
            competences.add("Java");
            competences.add("Architecture");

            for (String comp : competences) {
                if(!competenceRepository.existsByNomCompetence(comp)){
                    Competence competence = new Competence();
                    competence.setNomCompetence(comp);
                    competenceRepository.save(competence);
                    for (int i = 0; i < 20; i++) {
                        if (!userRepository.existsByEmail(index+"teacher@esprit.tn")) {
                            Utilisateur student = new Utilisateur();
                            student.setEmail(index+"teacher@esprit.tn");
                            student.setCin("0"+(9868476+index+i));
                            student.setNom("teacher");
                            student.setCompetence(competence);
                            student.setGender(this.generateRandomGender());
                            student.setDateofbirth(this.generateRandomDateOfBirth());
                            student.setPrenom("T"+index);
                            student.setRole(ERole.ROLE_TEACHER);
                            student.setMotDePasse(encoder.encode("0000"));
                            userRepository.save(student);
                            System.out.println(student.getEmail());
                            index += 1;
                        }
                    }
                }
            }




//            List<String> salles = new ArrayList<>();
//            salles.add("Salle A");
//            salles.add("Salle B");
//            salles.add("Salle C");
//            salles.add("Salle D");
//            salles.add("Salle F");
//            salles.add("Salle G");
//            salles.add("Salle H");
//            salles.add("Salle I");
//            salles.add("Salle G");
//            for (String s : salles) {
//                if(salleRepository.findByNom_salle(s)==null){
//                    Salle salleA = new Salle();
//                    salleA.setNom_salle(s);
//                    salleA.setCapacite(30);
//                    salleRepository.save(salleA);
//                }
//
//            }

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
//            List<SeanceClasse> seanceClasses = new ArrayList<>();
//            for (Classe classe : classeRepository.findAll()) {
//                for (Matiere matiere : matiereRepository.findAll()) {
//                    for (Salle salle : salleRepository.findAll()) {
//                        for (Utilisateur enseignant : userRepository.findAllByRole(ERole.ROLE_TEACHER)) {
//                            SeanceClasse seanceClasse = new SeanceClasse();
//                            seanceClasse.setHeureDebut(Instant.now());
//                            seanceClasse.setHeureFin(Instant.now().plusSeconds(3600));
//                            seanceClasse.setClasse(classe);
//                            seanceClasse.setMatiere(matiere);
//                            seanceClasse.setSalle(salle);
//                            seanceClasse.setEnseignant(enseignant);
//                            seanceclasseRepository.save(seanceClasse);
//                        }
//                    }
//                }
//            }

            List<String> badWords = Arrays.asList(
                    "badword1",
                    "badword2",
                    "badword3",
                    "baiser",
                    "bander",
                    "bigornette",
                    "bite",
                    "bitte",
                    "bloblos",
                    "bordel",
                    "bourré",
                    "bourrée",
                    "brackmard",
                    "branlage",
                    "branler",
                    "branlette",
                    "branleur",
                    "branleuse",
                    "brouter le cresson",
                    "caca",
                    "chatte",
                    "chiasse",
                    "chier",
                    "chiottes",
                    "clito",
                    "clitoris",
                    "con",
                    "connard",
                    "connasse",
                    "conne",
                    "couilles",
                    "cramouille",
                    "cul",
                    "déconne",
                    "déconner",
                    "emmerdant",
                    "emmerder",
                    "emmerdeur",
                    "emmerdeuse",
                    "enculé",
                    "enculée",
                    "enculeur",
                    "enculeurs",
                    "enfoiré",
                    "enfoirée",
                    "étron",
                    "fille de pute",
                    "fils de pute",
                    "folle",
                    "foutre",
                    "gerbe",
                    "gerber",
                    "gouine",
                    "grande folle",
                    "grogniasse",
                    "gueule",
                    "jouir",
                    "la putain de ta mère",
                    "MALPT",
                    "ménage à trois",
                    "merde",
                    "merdeuse",
                    "merdeux",
                    "meuf",
                    "nègre",
                    "negro",
                    "nique ta mère",
                    "nique ta race",
                    "palucher",
                    "pédale",
                    "pédé",
                    "péter",
                    "pipi",
                    "pisser",
                    "pouffiasse",
                    "pousse-crotte",
                    "putain",
                    "pute",
                    "ramoner",
                    "sac à foutre",
                    "sac à merde",
                    "salaud",
                    "salope",
                    "suce",
                    "tapette",
                    "tanche",
                    "teuch",
                    "tringler",
                    "trique",
                    "troncher",
                    "trou du cul",
                    "turlute",
                    "zigounette",
                    "zizi",
                    "سكس",
                    "طيز",
                    "شرج",
                    "لعق",
                    "لحس",
                    "مص",
                    "تمص",
                    "بيضان",
                    "ثدي",
                    "بز",
                    "بزاز",
                    "حلمة",
                    "مفلقسة",
                    "بظر",
                    "كس",
                    "فرج",
                    "شهوة",
                    "شاذ",
                    "مبادل",
                    "عاهرة",
                    "جماع",
                    "قضيب",
                    "زب",
                    "لوطي",
                    "لواط",
                    "سحاق",
                    "سحاقية",
                    "اغتصاب",
                    "خنثي",
                    "احتلام",
                    "نيك",
                    "متناك",
                    "متناكة",
                    "شرموطة",
                    "عرص",
                    "خول",
                    "قحبة",
                    "لبوة",
                    "2g1c",
                    "2 girls 1 cup",
                    "acrotomophilia",
                    "alabama hot pocket",
                    "alaskan pipeline",
                    "anal",
                    "anilingus",
                    "anus",
                    "apeshit",
                    "arsehole",
                    "ass",
                    "asshole",
                    "assmunch",
                    "auto erotic",
                    "autoerotic",
                    "babeland",
                    "baby batter",
                    "baby juice",
                    "ball gag",
                    "ball gravy",
                    "ball kicking",
                    "ball licking",
                    "ball sack",
                    "ball sucking",
                    "bangbros",
                    "bangbus",
                    "bareback",
                    "barely legal",
                    "barenaked",
                    "bastard",
                    "bastardo",
                    "bastinado",
                    "bbw",
                    "bdsm",
                    "beaner",
                    "beaners",
                    "beaver cleaver",
                    "beaver lips",
                    "beastiality",
                    "bestiality",
                    "big black",
                    "big breasts",
                    "big knockers",
                    "big tits",
                    "bimbos",
                    "birdlock",
                    "bitch",
                    "bitches",
                    "black cock",
                    "blonde action",
                    "blonde on blonde action",
                    "blowjob",
                    "blow job",
                    "blow your load",
                    "blue waffle",
                    "blumpkin",
                    "bollocks",
                    "bondage",
                    "boner",
                    "boob",
                    "boobs",
                    "booty call",
                    "brown showers",
                    "brunette action",
                    "bukkake",
                    "bulldyke",
                    "bullet vibe",
                    "bullshit",
                    "bung hole",
                    "bunghole",
                    "busty",
                    "butt",
                    "buttcheeks",
                    "butthole",
                    "camel toe",
                    "camgirl",
                    "camslut",
                    "camwhore",
                    "carpet muncher",
                    "carpetmuncher",
                    "chocolate rosebuds",
                    "cialis",
                    "circlejerk",
                    "cleveland steamer",
                    "clit",
                    "clitoris",
                    "clover clamps",
                    "clusterfuck",
                    "cock",
                    "cocks",
                    "coprolagnia",
                    "coprophilia",
                    "cornhole",
                    "coon",
                    "coons",
                    "creampie",
                    "cum",
                    "cumming",
                    "cumshot",
                    "cumshots",
                    "cunnilingus",
                    "cunt",
                    "darkie",
                    "date rape",
                    "daterape",
                    "deep throat",
                    "deepthroat",
                    "dendrophilia",
                    "dick",
                    "dildo",
                    "dingleberry",
                    "dingleberries",
                    "dirty pillows",
                    "dirty sanchez",
                    "doggie style",
                    "doggiestyle",
                    "doggy style",
                    "doggystyle",
                    "dog style",
                    "dolcett",
                    "domination",
                    "dominatrix",
                    "dommes",
                    "donkey punch",
                    "double dong",
                    "double penetration",
                    "dp action",
                    "dry hump",
                    "dvda",
                    "eat my ass",
                    "ecchi",
                    "ejaculation",
                    "erotic",
                    "erotism",
                    "escort",
                    "eunuch",
                    "fag",
                    "faggot",
                    "fecal",
                    "felch",
                    "fellatio",
                    "feltch",
                    "female squirting",
                    "femdom",
                    "figging",
                    "fingerbang",
                    "fingering",
                    "fisting",
                    "foot fetish",
                    "footjob",
                    "frotting",
                    "fuck",
                    "fuck buttons",
                    "fuckin",
                    "fucking",
                    "fucktards",
                    "fudge packer",
                    "fudgepacker",
                    "futanari",
                    "gangbang",
                    "gang bang",
                    "gay sex",
                    "genitals",
                    "giant cock",
                    "girl on",
                    "girl on top",
                    "girls gone wild",
                    "goatcx",
                    "goatse",
                    "god damn",
                    "gokkun",
                    "golden shower",
                    "goodpoop",
                    "goo girl",
                    "goregasm",
                    "grope",
                    "group sex",
                    "g-spot",
                    "guro",
                    "hand job",
                    "handjob",
                    "hard core",
                    "hardcore",
                    "hentai",
                    "homoerotic",
                    "honkey",
                    "hooker",
                    "horny",
                    "hot carl",
                    "hot chick",
                    "how to kill",
                    "how to murder",
                    "huge fat",
                    "humping",
                    "incest",
                    "intercourse",
                    "jack off",
                    "jail bait",
                    "jailbait",
                    "jelly donut",
                    "jerk off",
                    "jigaboo",
                    "jiggaboo",
                    "jiggerboo",
                    "jizz",
                    "juggs",
                    "kike",
                    "kinbaku",
                    "kinkster",
                    "kinky",
                    "knobbing",
                    "leather restraint",
                    "leather straight jacket",
                    "lemon party",
                    "livesex",
                    "lolita",
                    "lovemaking",
                    "make me come",
                    "male squirting",
                    "masturbate",
                    "masturbating",
                    "masturbation",
                    "menage a trois",
                    "milf",
                    "missionary position",
                    "mong",
                    "motherfucker",
                    "mound of venus",
                    "mr hands",
                    "muff diver",
                    "muffdiving",
                    "nambla",
                    "nawashi",
                    "negro",
                    "neonazi",
                    "nigga",
                    "nigger",
                    "nig nog",
                    "nimphomania",
                    "nipple",
                    "nipples",
                    "nsfw",
                    "nsfw images",
                    "nude",
                    "nudity",
                    "nutten",
                    "nympho",
                    "nymphomania",
                    "octopussy",
                    "omorashi",
                    "one cup two girls",
                    "one guy one jar",
                    "orgasm",
                    "orgy",
                    "paedophile",
                    "paki",
                    "panties",
                    "panty",
                    "pedobear",
                    "pedophile",
                    "pegging",
                    "penis",
                    "phone sex",
                    "piece of shit",
                    "pikey",
                    "pissing",
                    "piss pig",
                    "pisspig",
                    "playboy",
                    "pleasure chest",
                    "pole smoker",
                    "ponyplay",
                    "poof",
                    "poon",
                    "poontang",
                    "punany",
                    "poop chute",
                    "poopchute",
                    "porn",
                    "porno",
                    "pornography",
                    "prince albert piercing",
                    "pthc",
                    "pubes",
                    "pussy",
                    "queaf",
                    "queef",
                    "quim",
                    "raghead",
                    "raging boner",
                    "rape",
                    "raping",
                    "rapist",
                    "rectum",
                    "reverse cowgirl",
                    "rimjob",
                    "rimming",
                    "rosy palm",
                    "rosy palm and her 5 sisters",
                    "rusty trombone",
                    "sadism",
                    "santorum",
                    "scat",
                    "schlong",
                    "scissoring",
                    "semen",
                    "sex",
                    "sexcam",
                    "sexo",
                    "sexy",
                    "sexual",
                    "sexually",
                    "sexuality",
                    "shaved beaver",
                    "shaved pussy",
                    "shemale",
                    "shibari",
                    "shit",
                    "shitblimp",
                    "shitty",
                    "shota",
                    "shrimping",
                    "skeet",
                    "slanteye",
                    "slut",
                    "s&m",
                    "smut",
                    "snatch",
                    "snowballing",
                    "sodomize",
                    "sodomy",
                    "spastic",
                    "spic",
                    "splooge",
                    "splooge moose",
                    "spooge",
                    "spread legs",
                    "spunk",
                    "strap on",
                    "strapon",
                    "strappado",
                    "strip club",
                    "style doggy",
                    "suck",
                    "sucks",
                    "suicide girls",
                    "sultry women",
                    "swastika",
                    "swinger",
                    "tainted love",
                    "taste my",
                    "tea bagging",
                    "threesome",
                    "throating",
                    "thumbzilla",
                    "tied up",
                    "tight white",
                    "tit",
                    "tits",
                    "titties",
                    "titty",
                    "tongue in a",
                    "topless",
                    "tosser",
                    "towelhead",
                    "tranny",
                    "tribadism",
                    "tub girl",
                    "tubgirl",
                    "tushy",
                    "twat",
                    "twink",
                    "twinkie",
                    "two girls one cup",
                    "undressing",
                    "upskirt",
                    "urethra play",
                    "urophilia",
                    "vagina",
                    "venus mound",
                    "viagra",
                    "vibrator",
                    "violet wand",
                    "vorarephilia",
                    "voyeur",
                    "voyeurweb",
                    "voyuer",
                    "vulva",
                    "wank",
                    "wetback",
                    "wet dream",
                    "white power",
                    "whore",
                    "worldsex",
                    "wrapping men",
                    "wrinkled starfish",
                    "xx",
                    "xxx",
                    "yaoi",
                    "yellow showers",
                    "yiffy",
                    "zoophilia",
                    "🖕"
            );

            for (String word : badWords) {
                BadWord badWord = new BadWord();
                badWord.setWord(word);
                badWordRepository.save(badWord);
            }
        };
    }

    private String generateRandomDateOfBirth() {
        long minDay = LocalDate.of(1997, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2010, 1, 1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).toString();
    }

    private String generateRandomGender() {
        String[] genders = {"Male", "Female"};
        int randomIndex = ThreadLocalRandom.current().nextInt(genders.length);
        return genders[randomIndex];
    }

    public String generateRandomYear() {
        int minYear = 2019;
        int maxYear = LocalDate.now().getYear();
        Random random = new Random();
        Integer year = random.nextInt((maxYear - minYear) + 1) + minYear;
        return year.toString();
    }
}
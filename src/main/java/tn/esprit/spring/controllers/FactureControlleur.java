package tn.esprit.spring.controllers;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.apache.batik.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.Reglement;
import tn.esprit.spring.entities.Tranche;
import tn.esprit.spring.entities.status;
import tn.esprit.spring.repositories.FactureRepository;
import tn.esprit.spring.repositories.ReglementRepository;
import tn.esprit.spring.services.IReglementService;
import tn.esprit.spring.services.IfactureService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facture")
@AllArgsConstructor
public class FactureControlleur {
    @Autowired
    private IfactureService factureService;
    @Autowired
    private ReglementRepository reglementRepository;
    @Autowired
    private FactureRepository factureRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Facture>> getallFacture(){
        List<Facture> factures = factureService.getallFacture();
        return new ResponseEntity<>(factures, HttpStatus.OK);
    }
    /*
    @PostMapping("/upload/{id}")
    public Facture uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
        Reglement reglement = reglementRepository.findById(id).orElseThrow(() -> new RuntimeException("reglement non trouvé"));

        Facture facture = new Facture();
        facture.setReglement(reglement);

        facture.setFile(file.getBytes());
        factureRepository.save(facture);



        return  facture;
    }
*/
    @PostMapping("/upload/{id}")
    public ResponseEntity<Facture> uploadFile(@RequestParam("file") MultipartFile file,
                                              @PathVariable Long id,
                                              @RequestParam("date") String dateString,
                                              @RequestParam("montant") Double montant) {
        try {
            Reglement reglement = reglementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Règlement non trouvé"));

            Facture facture = new Facture();
            facture.setReglement(reglement);

            facture.setFile(file.getBytes());


            LocalDate date = LocalDate.parse(dateString);
            facture.setDate(date);

            facture.setMontant(montant); // Définir le montant

            Facture savedFacture = factureRepository.save(facture);

            return ResponseEntity.ok(savedFacture);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Facture> updateFile(@PathVariable Long id,
                                              @RequestParam(value = "file", required = false) MultipartFile file,
                                              @RequestParam(value = "date", required = false) String dateString,
                                              @RequestParam(value = "montant", required = false) Double montant) {
        try {
            Facture facture = factureRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

            if (file != null && !file.isEmpty()) {
                facture.setFile(file.getBytes());
            }

            if (dateString != null && !dateString.isEmpty()) {
                LocalDate date = LocalDate.parse(dateString);
                facture.setDate(date);
            }

            if (montant != null) {
                facture.setMontant(montant);
            }
            if (facture.getStatus() != status.NON_VALIDE || facture.getStatus() != status.EN_COURS_DE_TRAITEMENT) {
                facture.setStatus(status.EN_COURS_DE_TRAITEMENT);
            }

            Facture updatedFacture = factureRepository.save(facture);

            return ResponseEntity.ok(updatedFacture);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/update")
    public Facture updateFacture(@RequestBody Facture facture){
        Facture updateFacture = factureService.updatefacture(facture);
        return updateFacture;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable("id") Long id){
        factureService.removeFacture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        byte[] fileData = factureService.getFileData(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);

    }
    @PostMapping("/ajout/{id}")
    public Facture addaffecter(@RequestBody Facture fature, @PathVariable Long id) throws IOException {
        Reglement reglement = reglementRepository.findById(id).orElseThrow(() -> new RuntimeException("reglement Non trouvé"));
        Facture facture = new Facture();
        facture.setReglement(reglement);
       return factureRepository.save(facture);
    }
    @GetMapping("/listaffecter/{id}")
    public ResponseEntity<List<Facture>> getFacturesbyId(@PathVariable Long id) {

            List<Facture> factures = factureService.getFacturebyid(id);
            return ResponseEntity.ok(factures);

    }
    @GetMapping("/getfacturebyid/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        Optional<Facture> factureOptional = factureRepository.findById(id);
        return factureOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
@GetMapping("/montantrestant/{id}")
public Double montantrestant(@PathVariable Long id)
{
    List<Facture> factures = factureService.getFacturebyid(id);
    Double totalPaid = factures.stream()
            .mapToDouble(Facture::getMontant)
            .sum();
    return 6500 - totalPaid;
}
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generateRecu(@PathVariable("id") Long id) throws DocumentException {
        Facture facture = factureRepository.findById(id).orElse(null);
        if (facture == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] pdfBytes = factureService.generateRecu(facture);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "demande_stage.pdf");
            headers.setContentLength(pdfBytes.length);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pdffacture/{id}")
    public ResponseEntity<byte[]> generateFacture(@PathVariable("id") Long id) throws DocumentException {
        Reglement reglement = reglementRepository.findById(id).orElse(null);
        if (reglement == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] pdfBytes = factureService.generateFacture(reglement, id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "demande_stage.pdf");
            headers.setContentLength(pdfBytes.length);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/factures")
    public ResponseEntity<Page<Facture>> getFactures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<Facture> stagePage = factureRepository.findAll(paging);

        return ResponseEntity.ok(stagePage);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Facture>> searchStages(@RequestParam(required = false) Double montant,
                                                    @RequestParam(required = false) LocalDate date
                                                ) {
        List<Facture> Facture = factureService.searchStages(date, montant);
        return ResponseEntity.ok(Facture);
    }
    @PutMapping("/valider/{id}")
    public ResponseEntity<Void> validerFacture(@PathVariable Long id) {
        factureService.validerFacture(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refuser/{id}")
    public ResponseEntity<Void> refuserFacture(@PathVariable Long id) {
        factureService.refuserFacture(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/countFactureByStatus/{status}")
    public ResponseEntity<Long> countFactureByStatus(@PathVariable status status) {
        Long count = factureRepository.countFactureByStatus(status);
        return ResponseEntity.ok(count);
    }

}

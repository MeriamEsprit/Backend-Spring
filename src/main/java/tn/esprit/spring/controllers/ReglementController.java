package tn.esprit.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.ModeDePaiement;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Reglement;
import tn.esprit.spring.entities.Tranche;
import tn.esprit.spring.repositories.ReglementRepository;
import tn.esprit.spring.services.IReglementService;

import java.util.List;

@RestController
@RequestMapping("/reglement")
@AllArgsConstructor
public class ReglementController {
    @Autowired
    private IReglementService reglementService;
    @Autowired
    private ReglementRepository reglementRepository;

    @PostMapping("/addReglement")
    public Reglement addReglment(@RequestBody Reglement reglement)
    {
        return reglementService.addReglmement(reglement);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reglement>> getallReglement(){
        List<Reglement> reglements = reglementService.getallReglement();
        return new ResponseEntity<>(reglements, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public Reglement updateReglement(@PathVariable("id") Long id ,@RequestBody Reglement reglement){
        Reglement updateReglmement = reglementService.updateReglmement(id,reglement);
      return updateReglmement;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReglement(@PathVariable("id") Long id){
        reglementService.removeReglement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/findById/{id}")
    public Reglement getReglementById(@PathVariable("id") Long id) {
        return reglementRepository.findById(id).get();
    }

    /*
    @PostMapping("/addReglementAndAssignReglementToUser/{email}" )
    public Reglement addOffreAndAssignOffreToUser (@RequestBody Reglement reglement , @PathVariable String email) {
        return reglementService.addReglementAndAssignReglementToUser(email , reglement);
    }
    @GetMapping("/listaffecter/{email}")
    public ResponseEntity<List<Reglement>> getReglementsAffectesByEmail(@PathVariable String email) {
        List<Reglement> stages =reglementService.getReglementAffectesByEmail(email);
        return ResponseEntity.ok(stages);
    }*/

    @GetMapping("/filtrer")
    public List<Reglement> Filtrer(@RequestParam ModeDePaiement md)
    {

        return reglementService.findbymode(md);
    }

    @GetMapping("/reglements")
    public ResponseEntity<Page<Reglement>> getReglements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<Reglement> stagePage = reglementRepository.findAll(paging);

        return ResponseEntity.ok(stagePage);
    }

    @GetMapping("/countReglementByModeDePaiement/{modeDePaiement}")
    public ResponseEntity<Long> countReglementByModeDePaiement(@PathVariable ModeDePaiement modeDePaiement) {
        Long count = reglementRepository.countReglementByModeDePaiement(modeDePaiement);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countReglementByTranche/{tranche}")
    public ResponseEntity<Long> countReglementByTranche(@PathVariable Tranche tranche) {
        Long count = reglementRepository.countReglementByTranche(tranche);
        return ResponseEntity.ok(count);
    }
}

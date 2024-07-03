package tn.esprit.spring.services;

import com.itextpdf.text.DocumentException;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.Reglement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IfactureService {


    Facture addFacture(Facture facture);

    List<Facture> getallFacture();

    void removeFacture(Long idfacture);

    Facture updatefacture(Facture facture);

    List<Facture> getFacturebyid(Long Id);

    byte[] getFileData(Long id);

    byte[] generateRecu(Facture facture) throws DocumentException, IOException;

    byte[] generateFacture(Reglement reglement, Long id) throws DocumentException, IOException;

    List<Facture> searchStages(LocalDate date, Double montant);

    void validerFacture(Long id);

    void refuserFacture(Long id);
}

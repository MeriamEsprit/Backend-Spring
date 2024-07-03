package tn.esprit.spring.services;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.Reglement;
import tn.esprit.spring.entities.status;
import tn.esprit.spring.repositories.FactureRepository;
import tn.esprit.spring.repositories.ReglementRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FactureService implements IfactureService{
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    ReglementRepository reglementRepository;


    @Override
    public Facture addFacture(Facture facture)
    {
        return factureRepository.save(facture);
    }

    @Override
    public List<Facture> getallFacture() {
        return factureRepository.findAll();
        // return null;
    }

    @Override
    public void removeFacture(Long idfacture) {
        factureRepository.deleteById(idfacture);
    }


    @Override
    public Facture updatefacture(Facture facture) {
        Facture factureex = factureRepository.findById(facture.getId()).orElse(null);
        if (factureex != null) {
            factureex.setDate(facture.getDate()); // Set the new date
            factureex.setFile(facture.getFile()); // Set the new file
            factureex.setMontant(facture.getMontant()); // Set the new montant

            return factureRepository.save(factureex); // Save and return the updated facture
        }
        return null; // Return null if the facture was not found
    }
    @Override
    public List<Facture> getFacturebyid(Long id) {
        Reglement reglement = reglementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reglement not found with ID: " + id));

        return new ArrayList<>(reglement.getFacteurs());
    }
    @Override
    public byte[] getFileData(Long id) {
        Facture facture = factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture not found"));
        return facture.getFile();
    }
    public String getCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return  date.format(formatter);
    }

    @Override
    public byte[] generateRecu(Facture facture) throws DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        String signatureImagePath = "classpath:/static/logo.jpg";
        Image signatureImage = Image.getInstance(signatureImagePath);
        signatureImage.scaleAbsolute(200, 100);
        signatureImage.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(signatureImage);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Paragraph date = new Paragraph("Tunis, le " + getCurrentDate(),fontBold);
        date.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(date);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));


        Paragraph Titre = new Paragraph("Reçu ",fontBold );
        Titre.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(Titre);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        Paragraph objet = new Paragraph("Date pièce:" +facture.getDate(),fontBold);
        document.add(objet);
        document.add(new Paragraph("\n"));

        Paragraph intro = new Paragraph("Je soussigné(e) : USERSYSTEM",fontBold);
        document.add(intro);
        document.add(new Paragraph("\n"));

        Paragraph intro1 = new Paragraph("Trésorièr(e) de : ESPRIT",fontBold);
        document.add(intro1);
        document.add(new Paragraph("\n"));

        Paragraph intro2 = new Paragraph("Certifie avoir reçu de (Mr/ Mme) : Asma Mnasri",fontBold);
        document.add(intro2);
        document.add(new Paragraph("\n"));
        Paragraph intro3 = new Paragraph("La somme de :" +facture.getMontant(),fontBold);
        document.add(intro3);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));



        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] generateFacture(Reglement reglement, Long id) throws DocumentException, IOException {
        List<Facture> factures = getFacturebyid(id);
        Double totalPaid = factures.stream()
                .filter(facture -> facture.getStatus() == status.VALIDE)
                .mapToDouble(Facture::getMontant)
                .sum();
         Double Reste= 6500 - totalPaid;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        String signatureImagePath = "classpath:/static/logo.jpg";
        Image signatureImage = Image.getInstance(signatureImagePath);
        signatureImage.scaleAbsolute(200, 100);
        signatureImage.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(signatureImage);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Paragraph date = new Paragraph("Tunis, le " + getCurrentDate(),fontBold);
        date.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(date);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));


        Paragraph Titre = new Paragraph("Facture ",fontBold );
        Titre.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(Titre);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));


        Paragraph intro = new Paragraph("Je soussigné(e) : USERSYSTEM",fontBold);
        document.add(intro);
        document.add(new Paragraph("\n"));

        Paragraph intro1 = new Paragraph("Trésorièr(e) de : ESPRIT",fontBold);
        document.add(intro1);
        document.add(new Paragraph("\n"));

        Paragraph intro2 = new Paragraph("Certifie avoir reçu de (Mr/ Mme) : Asma Mnasri",fontBold);
        document.add(intro2);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2); // 2 columns

// Header row

        PdfPCell cellDate = new PdfPCell(new Phrase("Date", fontBold));
        PdfPCell cellMontant = new PdfPCell(new Phrase("Montant", fontBold));

        cellDate.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellMontant.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cellDate);
        table.addCell(cellMontant);

// Assuming reglement.getFactures() returns a list of Facture objects related to this payment
        for (Facture reglementFacture : reglement.getFacteurs()) {
            if (reglementFacture.getStatus() == status.VALIDE) {
            table.addCell(reglementFacture.getDate().toString());
            table.addCell(reglementFacture.getMontant().toString());}
        }

        document.add(table);
            document.add(new Paragraph("\n"));
        document.add(new Paragraph("Le reste à payer est: "+Reste,fontBold));

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));




        document.close();
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public List<Facture> searchStages(LocalDate date, Double montant) {
        Specification<Facture> spec = Specification.where(null);

        if (montant != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("montant"), montant));
        }
        if (date != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("date"), date));
        }

        return factureRepository.findAll(spec);
    }
    @Override
    public void validerFacture(Long id) {
        factureRepository.findById(id).ifPresent(facture -> {
            facture.validerFacture();
            factureRepository.save(facture);
        });
    }

    @Override
    public void refuserFacture(Long id) {
        factureRepository.findById(id).ifPresent(facture -> {
            facture.refuserFacture();
            factureRepository.save(facture);
        });
    }


}

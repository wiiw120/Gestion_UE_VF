package entities;

import javax.xml.bind.annotation.*;

/** Vue d'un étudiant SANS l'option, utilisée pour la réponse XML de GET /etudiants/option */
@XmlRootElement(name = "etudiant")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"anneeEtude", "email", "identifiant", "nom", "prenom"})
public class EtudiantXml {
    private int anneeEtude;
    private String email;
    private String identifiant;
    private String nom;
    private String prenom;

    public EtudiantXml() {}

    public EtudiantXml(Etudiant e) {
        this.anneeEtude = e.getAnneeEtude();
        this.email = e.getEmail();
        this.identifiant = e.getIdentifiant();
        this.nom = e.getNom();
        this.prenom = e.getPrenom();
    }
}

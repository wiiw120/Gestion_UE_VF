package entities;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "etudiants")
@XmlAccessorType(XmlAccessType.FIELD)
public class EtudiantList {
    @XmlElement(name = "etudiant")
    private List<EtudiantXml> etudiants;

    public EtudiantList() {}

    public EtudiantList(List<EtudiantXml> etudiants) {
        this.etudiants = etudiants;
    }

    public List<EtudiantXml> getEtudiants() { return etudiants; }
    public void setEtudiants(List<EtudiantXml> etudiants) { this.etudiants = etudiants; }
}

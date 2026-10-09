package metiers;

import entities.Etudiant;
import entities.Option;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EtudiantBusiness {
    private static final List<Etudiant> etudiants = new ArrayList<Etudiant>();
    private final OptionBusiness optionBusiness = new OptionBusiness();

    static {
        OptionBusiness ob = new OptionBusiness();
        etudiants.add(new Etudiant("I001", "Doe", "Jean", ob.getOptionByCode(1), 2023, "jean.doe@example.com"));
        etudiants.add(new Etudiant("I002", "Smith", "Alice", ob.getOptionByCode(1), 2022, "alice.smith@example.com"));
        etudiants.add(new Etudiant("I003", "Durand", "Pierre", ob.getOptionByCode(2), 2023, "pierre.durand@example.com"));
    }

    public EtudiantBusiness() {}

    // Ajouter un etudiant (false si l'option n'existe pas)
    public boolean addEtudiant(Etudiant etudiant) {
        if (etudiant.getOption() == null) return false;
        Option option = optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption());
        if (option == null) return false;
        etudiant.setOption(option);
        return etudiants.add(etudiant);
    }

    public Etudiant getEtudiantByIdentifiant(String identifiant) {
        for (Etudiant e : etudiants) {
            if (e.getIdentifiant().equals(identifiant)) return e;
        }
        return null;
    }

    public List<Etudiant> getEtudiantsByOption(Option option) {
        List<Etudiant> result = new ArrayList<>();
        for (Etudiant e : etudiants) {
            if (e.getOption() != null && e.getOption().getCodeOption() == option.getCodeOption()) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiants;
    }

    // Mettre à jour un etudiant (false si étudiant ou option introuvable)
    public boolean updateEtudiant(String identifiant, Etudiant updatedEtudiant) {
        if (updatedEtudiant.getOption() == null) return false;
        Option option = optionBusiness.getOptionByCode(updatedEtudiant.getOption().getCodeOption());
        if (option == null) return false;
        for (int i = 0; i < etudiants.size(); i++) {
            if (etudiants.get(i).getIdentifiant().equals(identifiant)) {
                updatedEtudiant.setIdentifiant(identifiant);
                updatedEtudiant.setOption(option);
                etudiants.set(i, updatedEtudiant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEtudiant(String identifiant) {
        Iterator<Etudiant> iterator = etudiants.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIdentifiant().equals(identifiant)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}

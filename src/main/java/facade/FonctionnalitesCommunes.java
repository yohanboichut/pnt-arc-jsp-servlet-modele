package facade;

import exceptions.MauvaisIdentifiantsException;
import modele.Utilisateur;

public interface FonctionnalitesCommunes {


    Utilisateur getUtilisateur(String email, String cleUtilisateur) throws MauvaisIdentifiantsException;
}

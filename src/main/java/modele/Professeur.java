package modele;

import java.util.Collection;

public class Professeur extends AbstractUtilisateur{
    public Professeur(String email, String pseudo, String cleAuthentification) {
        super(email, pseudo, cleAuthentification, TypeUtilisateur.PROFESSEUR_NON_VERIFIE);
    }







}

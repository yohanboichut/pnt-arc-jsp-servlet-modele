package modele;

import java.util.ArrayList;
import java.util.Collection;

public class Etudiant extends AbstractUtilisateur{
    public Etudiant(String email, String pseudo, String cleAuthentification) {
        super(email, pseudo, cleAuthentification, TypeUtilisateur.ETUDIANT);
    }


    private Collection<Score> scores = new ArrayList<>();

    public Collection<Score> getScores() {
        return scores;
    }

    public void ajouterResultat(Score score) {
        scores.add(score);
    }
}

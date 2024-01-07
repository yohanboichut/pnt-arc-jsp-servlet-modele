package modele;

import exceptions.ReponseIncorrecteException;

import java.util.ArrayList;
import java.util.Collection;

public class QuestionItem {

    String question;
    String[] reponses;
    String bonneReponse;

    Collection<Utilisateur>[] reponsesUtilisateurs;

    public QuestionItem(String question, String[] reponses, String bonneReponse) {
        this.question = question;
        this.reponses = reponses;
        this.bonneReponse = bonneReponse;
        this.reponsesUtilisateurs = new Collection[reponses.length];
        for (int i = 0; i < reponses.length; i++) {
            reponsesUtilisateurs[i] = new ArrayList<>();
        }
    }

    public String getQuestion() {
        return question;
    }

    public String[] getReponses() {
        return reponses;
    }

    public void repondre(Utilisateur utilisateur, int idReponse) throws ReponseIncorrecteException {
        if (idReponse < 0 || idReponse >= reponses.length)
            throw new ReponseIncorrecteException();
        reponsesUtilisateurs[idReponse].add(utilisateur);
    }
}

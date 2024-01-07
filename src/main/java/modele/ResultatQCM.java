package modele;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResultatQCM {


    private Map<Etudiant,Score> scores=new HashMap<>();

    public ResultatQCM(QuestionItem[] questions, Collection<Etudiant> participants) {
        participants.stream().forEach(participant -> scores.put(participant, new Score(questions.length)));
        for(QuestionItem question : questions){
            for (int i= 0;i<question.reponses.length;i++){
                if (question.reponses[i].equals(question.bonneReponse)){
                    for (Utilisateur participant : question.reponsesUtilisateurs[i]){
                        scores.get(participant).ajouterBonneReponse();
                    }
                }
            }
        }
        scores.entrySet().stream().forEach(entry -> entry.getKey().ajouterResultat(entry.getValue()));
    }

    public Map<Etudiant,Score> getScores(){
        return scores;
    }
}

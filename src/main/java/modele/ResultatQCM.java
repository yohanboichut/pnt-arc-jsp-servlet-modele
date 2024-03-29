package modele;

import dto.EtudiantDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResultatQCM {


    private Map<EtudiantDTO,Score> scores=new HashMap<>();

    public ResultatQCM(String cleQCM, String description, QuestionItem[] questions, Collection<Etudiant> participants) {
        Map<Etudiant,EtudiantDTO> mapping = new HashMap<>();
        participants.stream().forEach(participant ->
        {
            EtudiantDTO dto = new EtudiantDTO(participant.getEmail(), participant.getPseudo());
            mapping.put(participant,dto);
            scores.put(dto, new Score(cleQCM, description, questions.length));
        });
        for(QuestionItem question : questions){
            for (int i= 0;i<question.reponses.length;i++){
                if (question.reponses[i].equals(question.bonneReponse)){
                    for (Utilisateur participant : question.reponsesUtilisateurs[i]){

                        scores.get(mapping.get(participant)).ajouterBonneReponse();
                    }
                }
            }
        }
        participants.stream().forEach(participant -> {scores.entrySet().stream().filter(x -> x.getKey().getEmail().equals(participant.getEmail())).forEach(x -> participant.ajouterResultat(x.getValue()));});

    }

    public Map<EtudiantDTO,Score> getScores(){
        return scores;
    }
}

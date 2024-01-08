package dto;

import modele.QCM;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class QCMDTO {


    private String cleQCM;

    private String description;

    private LocalDateTime dateQCM;

    private long tempsDisponibleMillisecondes;

    private QuestionDTO[] questions;

    private Collection<String> participants = new ArrayList<>();


    public QCMDTO(){

    }

    public QCMDTO(QCM qcm){
        this.dateQCM = qcm.getDateQCM();
        this.description = qcm.getDescription();
        this.cleQCM = qcm.getCleQCM();
        this.tempsDisponibleMillisecondes = qcm.getTempsDisponibleSecondes();
        this.questions = new QuestionDTO[qcm.getQuestions().length];
        this.participants = qcm.getParticipants().stream().map(x -> x.getPseudo()).collect(Collectors.toList());

        for (int i = 0; i < qcm.getQuestions().length; i++) {
            this.questions[i] = new QuestionDTO(qcm.getQuestions()[i].getQuestion(), qcm.getQuestions()[i].getReponses());
        }

    }


    public String getCleQCM() {
        return cleQCM;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateQCM() {
        return dateQCM;
    }

    public long getTempsDisponibleMillisecondes() {
        return tempsDisponibleMillisecondes;
    }

    public QuestionDTO[] getQuestions() {
        return questions;
    }

    public Collection<String> getParticipants() {
        return participants;
    }
}

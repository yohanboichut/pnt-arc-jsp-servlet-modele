package modele;

import exceptions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class QCM {


    private Etudiant createur;
    private String cleQCM;

    private String description;

    private boolean valide = false;

    private LocalDateTime dateQCM;

    private long tempsDisponibleSecondes;

    private QuestionItem[] questions;

    private Collection<Etudiant> participants = new ArrayList<>();

    private ResultatQCM resultat;




    public QCM(Etudiant createur,String description, long tempsDisponible, Question[] questions) {
        this.createur = createur;
        cleQCM = UUID.randomUUID().toString();
        this.description = description;
        this.tempsDisponibleSecondes = tempsDisponible;
        this.questions = new QuestionItem[questions.length];
        for (int i = 0; i < questions.length; i++) {
            this.questions[i] = new QuestionItem(questions[i].question(),
                    questions[i].reponses(), questions[i].bonneReponse());
        }
    }

    public Etudiant getCreateur() {
        return createur;
    }

    public void inscription(Etudiant participant) throws QCMDejaInscritException, QCMTermineException {
        if (LocalDateTime.now().isAfter(dateQCM.plusSeconds(tempsDisponibleSecondes)))
            throw new QCMTermineException();
        if (participants.contains(participant))
            throw new QCMDejaInscritException();
        participants.add(participant);
    }

    public String getCleQCM() {
        return cleQCM;
    }

    public void valider() {
        this.valide = true;
    }

    public boolean estValide(){
        return this.valide;
    }

    public String getDescription() {
        return description;
    }

    public void publierQCM(){
        this.dateQCM = LocalDateTime.now();
    }


    /**
     * Cette méthode permet à un utilisateur de répondre à une question.
     *
     * @param utilisateur l'utilisateur qui répond à la question
     * @param idReponse   l'ID de la réponse choisie par l'utilisateur
     * @throws ReponseIncorrecteException si l'ID de réponse fourni est invalide
     */

    public void repondre(Etudiant utilisateur, int idQuestion, int idReponse) throws ReponseIncorrecteException, QCMTermineException, EtudiantNonInscritException {
        if (!participants.contains(utilisateur))
            throw new EtudiantNonInscritException();
        if (LocalDateTime.now().isAfter(dateQCM.plusSeconds(tempsDisponibleSecondes)))
            throw new QCMTermineException();
        if (idQuestion < 0 || idQuestion >= questions.length)
            throw new ReponseIncorrecteException();
        questions[idQuestion].repondre(utilisateur, idReponse);
    }


    public boolean estPublie() {
        return !Objects.isNull(dateQCM);
    }

    public ResultatQCM calculerResultat() throws OperationNonAutoriseeException {
        if (!estPublie())
            throw new OperationNonAutoriseeException();
        if (LocalDateTime.now().isBefore(dateQCM.plusSeconds(tempsDisponibleSecondes)))
            throw new OperationNonAutoriseeException();

        if (Objects.isNull(resultat)) {
            resultat = new ResultatQCM(cleQCM,description,questions, participants);
        }
        return resultat;
    }

    public ResultatQCM getResultat() throws ResultatsNonCalculesException {
        if (Objects.isNull(resultat)) {
            throw new ResultatsNonCalculesException();
        }
        return resultat;
    }


    public Collection<Etudiant> getParticipants() {
        return participants;
    }

    public LocalDateTime getDateQCM() {
        return dateQCM;
    }


    public long getTempsDisponibleSecondes(){
        return tempsDisponibleSecondes;
    }

    public QuestionItem[] getQuestions() {
        return questions;
    }
}

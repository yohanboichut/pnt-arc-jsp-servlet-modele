package modele;

public class Question {
    String question;
    String[] reponses;
    String bonneReponse;

    public Question(String question, String[] reponses, String bonneReponse) {
        this.question = question;
        this.reponses = reponses;
        this.bonneReponse = bonneReponse;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getReponses() {
        return reponses;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }
}

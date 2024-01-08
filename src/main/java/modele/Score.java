package modele;

public class Score {

    private int nbQuestions;

    private int nbBonnesReponses;

    private String cleQCM;
    private String description;


    public Score(String cleQCM, String description, int nbQuestions) {
        this.cleQCM = cleQCM;
        this.description = description;
        this.nbQuestions = nbQuestions;
        this.nbBonnesReponses = 0;
    }

    public void ajouterBonneReponse() {
        nbBonnesReponses++;
    }


    public double getScorePrecision() {
        return ((double) nbBonnesReponses / nbQuestions)*100;
    }

    public String getCleQCM() {
        return cleQCM;
    }

    public String getDescription() {
        return description;
    }
}

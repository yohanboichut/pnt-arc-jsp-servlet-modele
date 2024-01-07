package modele;

public class Score {

    private int nbQuestions;

    private int nbBonnesReponses;


    public Score(int nbQuestions) {
        this.nbQuestions = nbQuestions;
        this.nbBonnesReponses = 0;
    }

    public void ajouterBonneReponse() {
        nbBonnesReponses++;
    }


    public double getScorePrecision() {
        return ((double) nbBonnesReponses / nbQuestions)*100;
    }


}

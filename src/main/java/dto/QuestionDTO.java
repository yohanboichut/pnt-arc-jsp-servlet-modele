package dto;

public class QuestionDTO {
    private String question;
    private String[] reponses;

    public QuestionDTO(String question, String[] reponses) {
        this.question = question;
        this.reponses = reponses;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getReponses() {
        return reponses;
    }
}


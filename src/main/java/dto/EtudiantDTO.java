package dto;

public class EtudiantDTO  {
    private String email;
    private String pseudo;

    public EtudiantDTO(String email, String pseudo) {
        this.email = email;
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public String getPseudo() {
        return pseudo;
    }
}

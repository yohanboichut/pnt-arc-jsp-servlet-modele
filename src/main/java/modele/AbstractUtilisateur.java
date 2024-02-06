package modele;

public abstract class AbstractUtilisateur implements Utilisateur {

    private String email;
    private String pseudo;
    private String cleAuthentification;
    private TypeUtilisateur typeUtilisateur;

    @Override
    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public AbstractUtilisateur(String email, String pseudo, String cleAuthentification, TypeUtilisateur typeUtilisateur) {
        this.email = email;
        this.pseudo = pseudo;
        this.cleAuthentification = cleAuthentification;
        this.typeUtilisateur = typeUtilisateur;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPseudo() {
        return pseudo;
    }

    @Override
    public String getCleAuthentification() {
        return cleAuthentification;
    }

    @Override
    public String getTypeUtilisateur() {
        return typeUtilisateur.name();
    }
}

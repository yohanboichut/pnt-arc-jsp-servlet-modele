package modele;

public interface Utilisateur {

    /**
     *
     * @param typeUtilisateur : permet de modifier le type d'utilisateur en particulier pour les professeurs
     *                        avec leurs deux états
     */
    void setTypeUtilisateur(TypeUtilisateur typeUtilisateur);
    /**
     * retourne l'email
     * @return  l'email de l'utilisateur
     */
    String getEmail();

    /**
     *
     * @return le pseudo de l'utilisateur
     */
    String getPseudo();

    /**
     *
     * @return la clé d'authentification de l'utilisateur
     */
    String getCleAuthentification();

    /**
     * @return le type d'utilisateur
     */
    String getTypeUtilisateur();

    public enum TypeUtilisateur {
        ETUDIANT,
        PROFESSEUR,
        PROFESSEUR_NON_VERIFIE
    }
}

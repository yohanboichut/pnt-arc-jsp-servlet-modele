package facade;

import exceptions.*;
import modele.QCM;
import modele.Utilisateur;

import java.util.Collection;

public interface FacadeGestionProfesseur {


    /**
     * Inscription d'un professeur.
     *
     * @param  email  l'email du professeur
     * @param  pseudo le pseudo du professeur
     * @return        la clé d'authentification
     * @throws EmailMalFormeException   si l'email est mal formé
     * @throws PseudoManquantException si le pseudo est manquant
     */

    String inscriptionProfesseur(String email, String pseudo) throws EmailMalFormeException, PseudoManquantException;


    /**
     * Récupère une collection de QCM à valider.
     *
     * @param cleUtilisateur    la clé de l'utilisateur doit correspondre à un professeur
     * @return                  la collection de QCM à valider
     * @throws CompteNonConfirmeException        si le compte n'est pas confirmé
     * @throws OperationNonAutoriseeException    si l'opération n'est pas autorisée
     * @throws UtilisateurInexistantException    si l'utilisateur n'existe pas
     */

    Collection<QCM> getQCMsAValider(String cleUtilisateur) throws CompteNonConfirmeException, OperationNonAutoriseeException, UtilisateurInexistantException;

    /**
     * Valide le QCM pour un utilisateur donné et une clé de QCM.
     *
     * @param cleUtilisateur    la clé de l'utilisateur doit correspondre à un professeur
     * @param  cleQCM          la clé du QCM
     * @throws CompteNonConfirmeException     si le compte de l'utilisateur n'est pas confirmé
     * @throws OperationNonAutoriseeException si l'opération n'est pas autorisée
     * @throws QCMInexistantException         si le QCM n'existe pas
     * @throws UtilisateurInexistantException si l'utilisateur n'existe pas
     */

    void validerQCM(String cleUtilisateur, String cleQCM) throws CompteNonConfirmeException, OperationNonAutoriseeException, QCMInexistantException, UtilisateurInexistantException;


    /**
     * Refuse un QCM pour un utilisateur donné.
     *
     * @param cleUtilisateur    la clé de l'utilisateur doit correspondre à un professeur
     * @param cleQCM            la clé du QCM
     * @throws CompteNonConfirmeException       si le compte n'est pas confirmé
     * @throws OperationNonAutoriseeException   si l'opération n'est pas autorisée
     * @throws QCMInexistantException           si le QCM n'existe pas
     * @throws UtilisateurInexistantException   si l'utilisateur n'existe pas
     */

    void refuserQCM(String cleUtilisateur, String cleQCM) throws CompteNonConfirmeException, OperationNonAutoriseeException, QCMInexistantException, UtilisateurInexistantException;


    /**
     * Récupère une collection d'objets Utilisateur à valider.
     *
     * @param cleUtilisateur    la clé de l'utilisateur doit correspondre à un professeur
     * @return                                  une collection d'objets Utilisateur à valider
     * @throws OperationNonAutoriseeException   si l'opération n'est pas autorisée
     * @throws CompteNonConfirmeException       si le compte n'est pas confirmé
     * @throws UtilisateurInexistantException  si l'utilisateur n'existe pas
     */

    Collection<Utilisateur> getUtilisateursAValider(String cleUtilisateur) throws OperationNonAutoriseeException, CompteNonConfirmeException, UtilisateurInexistantException;


    /**
     * Valide un utilisateur avec la clé utilisateur donnée et la clé utilisateur à valider.
     *
     * @param  cleUtilisateur             la clé utilisateur doit correspondre à un professeur déjà validé
     * @param  cleUtilisateurAValider     la clé utilisateur à valider
     * @throws OperationNonAutoriseeException  si l'opération n'est pas autorisée
     * @throws CompteNonConfirmeException     si le compte n'est pas confirmé
     * @throws UtilisateurInexistantException si l'utilisateur n'existe pas
     */
    void validerUtilisateur(String cleUtilisateur, String cleUtilisateurAValider) throws OperationNonAutoriseeException, CompteNonConfirmeException, UtilisateurInexistantException;
}

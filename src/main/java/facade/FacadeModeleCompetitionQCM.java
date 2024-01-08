package facade;

import dto.QCMDTO;
import exceptions.*;
import modele.*;

import java.util.Collection;

public interface FacadeModeleCompetitionQCM {

    /**
     * Permet d'inscrire un utilisateur
     * @param email : email de l'utilisateur
     * @param pseudo : pseudo de l'utilisateur
     * @return clé authentification
     */
    String inscription(String email, String pseudo) throws EmailMalFormeException, PseudoManquantException;


    /**
     * Permet de creer un QCM pour un etudiant
     * @param cleEtudiant : la clé de l'etudiant
     * @param description : la description du QCM
     * @param tempsDisponible : le temps disponible pour le QCM en millisecondes
     * @param questions : les questions comprenant les réponses possibles et la bonne reponse
     * @return la clé du QCM
     */
    String creerQCM(String cleEtudiant, String description, long tempsDisponible, Question[] questions)
            throws UtilisateurInexistantException, OperationNonAutoriseeException, InformationManquanteException,
            TempsIncoherentException, QuestionsIncoherentesException, CompteNonConfirmeException;



    /**
     * Récupère la collection des QCM en attente de validation par un professeur pour un étudiant donné.
     *
     * @param  cleEtudiant  l'identifiant unique de l'étudiant
     * @return              une collection de QCM en attente de validation
     * @throws UtilisateurInexistantException    si l'étudiant n'existe pas
     * @throws OperationNonAutoriseeException    si l'opération n'est pas autorisée
     */
    Collection<QCM> getMesQCMsEnAttenteDeValidation(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException;



    /**
     * Récupère une collection d'objets QCM qui ont déjà fait l'objet d'une publication et qui sont terminés.
     *
     * @param  cleEtudiant  la clé unique de l'étudiant
     * @return              une collection d'objets QCM
     * @throws UtilisateurInexistantException    si l'étudiant n'existe pas
     * @throws OperationNonAutoriseeException    si l'opération n'est pas autorisée
     */
    Collection<QCM> getMesQCMsTermines(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException;


    /**
     * Récupère une collection d'objets QCM prêts à être publiés.
     *
     * @param  cleEtudiant  la clé unique de l'étudiant
     * @return              une collection d'objets QCM
     * @throws UtilisateurInexistantException    si l'étudiant n'existe pas
     * @throws OperationNonAutoriseeException    si l'opération n'est pas autorisée
     */
    Collection<QCM> getMesQCMsPretsAEtrePublies(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException;


    /**
     * Publie un QCM.
     *
     * @param  cleEtudiant    la clé de l'étudiant
     * @param  cleQCM         la clé du QCM
     * @throws UtilisateurInexistantException    si l'utilisateur n'existe pas
     * @throws OperationNonAutoriseeException    si l'opération n'est pas autorisée si par exemple le créateur n'est pas l'étudiant
     * demandant l'action de publier
     * @throws QCMInexistantException            si le QCM n'existe pas
     */

    void publierQCM(String cleEtudiant,String cleQCM) throws UtilisateurInexistantException,
            OperationNonAutoriseeException, QCMInexistantException;


    /**
     * Inscrire un étudiant à un questionnaire à choix multiples (QCM).
     *
     * @param  cleEtudiant  l'ID de l'étudiant
     * @param  cleQCM       l'ID du QCM
     * @throws UtilisateurInexistantException        si l'étudiant n'existe pas
     * @throws OperationNonAutoriseeException        si l'opération n'est pas autorisée
     * @throws QCMInexistantException                si le QCM n'existe pas
     * @throws QCMDejaInscritException               si l'étudiant est déjà inscrit au QCM
     * @throws QCMTermineException                   si le QCM est déjà terminé
     * @throws ParticipationInterditePourCreateurException  si le créateur n'est pas autorisé à participer
     */
    void inscriptionQCM(String cleEtudiant, String cleQCM) throws UtilisateurInexistantException, OperationNonAutoriseeException, QCMInexistantException,
            QCMDejaInscritException, QCMTermineException, ParticipationInterditePourCreateurException;


    /**
     * Répond à une question à choix multiples.
     *
     * @param  cleEtudiant   la clé de l'étudiant
     * @param  cleQCM        la clé du QCM
     * @param  idQuestion    l'ID de la question
     * @param  idReponse     l'ID de la réponse
     * @throws QCMInexistantException        si le QCM n'existe pas
     * @throws ReponseIncorrecteException    si la réponse est incorrecte
     * @throws UtilisateurInexistantException    si l'utilisateur n'existe pas
     * @throws OperationNonAutoriseeException    si l'opération n'est pas autorisée
     * @throws EtudiantNonInscritException    si l'étudiant n'est pas inscrit
     * @throws QCMTermineException    si le QCM est terminé
     */

    void repondreQCM(String cleEtudiant, String cleQCM, int idQuestion, int idReponse) throws QCMInexistantException, ReponseIncorrecteException, UtilisateurInexistantException, OperationNonAutoriseeException,
            EtudiantNonInscritException, QCMTermineException;


    /**
     * Calcule le résultat pour une clé d'étudiant donnée et une clé de QCM donnée.
     *
     * @param  cleEtudiant    la clé de l'étudiant
     * @param  cleQCM         la clé du QCM
     * @throws QCMInexistantException         si le QCM n'existe pas
     * @throws UtilisateurInexistantException si l'étudiant n'existe pas
     * @throws OperationNonAutoriseeException si l'opération n'est pas autorisée
     */

    void calculerResultat(String cleEtudiant, String cleQCM) throws QCMInexistantException,  UtilisateurInexistantException, OperationNonAutoriseeException;


    /**
     +     * Obtenez le résultat du QCM pour un étudiant spécifique.
     +     *
     +     * @param  cleEtudiant                      l'identifiant unique de l'étudiant
     +     * @param  cleQCM                           l'identifiant unique du QCM
     +     * @return                                  le résultat du QCM
     +     * @throws UtilisateurInexistantException   si l'étudiant n'existe pas
     +     * @throws OperationNonAutoriseeException   si l'opération n'est pas autorisée
     +     * @throws EtudiantNonInscritException      si l'étudiant n'est pas inscrit
     +     * @throws QCMInexistantException           si le QCM n'existe pas
     +     * @throws ResultatsNonCalculesException    si les résultats n'ont pas été calculés
     +     */
    ResultatQCM getResultat(String cleEtudiant,String cleQCM) throws UtilisateurInexistantException, OperationNonAutoriseeException,
            EtudiantNonInscritException, QCMInexistantException, ResultatsNonCalculesException;


    /**
     * Récupère une collection d'objets QCMExtrait prêts pour les compétitions.
     *
     * @param  cleEtudiant  la clé de l'étudiant
     * @return              la collection d'objets QCMExtrait
     * @throws OperationNonAutoriseeException  si l'opération n'est pas autorisée
     * @throws UtilisateurInexistantException si l'utilisateur n'existe pas
     */
    Collection<QCMDTO> getQCMsPretsPourCompetitions(String cleEtudiant) throws OperationNonAutoriseeException, UtilisateurInexistantException;


    /**
     * Récupère un objet QCMExtrait basé sur la clé d'étudiant et la clé de QCM données.
     *
     * @param  cleEtudiant                  la clé de l'étudiant
     * @param  cleQCM                       la clé du QCM
     * @return                              l'objet QCMExtrait
     * @throws OperationNonAutoriseeException si l'opération n'est pas autorisée
     * @throws UtilisateurInexistantException si l'utilisateur n'existe pas
     * @throws QCMInexistantException         si le QCM n'existe pas
     */

    QCMDTO getQCM(String cleEtudiant, String cleQCM) throws OperationNonAutoriseeException, UtilisateurInexistantException, QCMInexistantException;





    Collection<Score> getMesScores(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException;

}

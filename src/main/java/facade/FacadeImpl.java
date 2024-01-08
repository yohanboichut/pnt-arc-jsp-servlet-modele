package facade;

import dto.QCMDTO;
import exceptions.*;
import modele.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FacadeImpl implements FacadeGestionProfesseur, FacadeModeleCompetitionQCM, FonctionnalitesCommunes{

    Map<String, Utilisateur> utilisateurs = new HashMap<>();

    Map<String, Utilisateur> professeursAValider = new HashMap<>();




    Collection<QCM> qcmsAValider = new ArrayList<>();

    // QCM qui ne sont pas ceux à valider
    Collection<QCM> qcms = new ArrayList<>();



    public FacadeImpl() {
        String cleAuthentification = "admin";
        String email="admin@admin.com";
        String pseudo="admin";
        Professeur professeur = new Professeur(email, pseudo, cleAuthentification);
        this.utilisateurs.put(cleAuthentification, professeur);
        professeur.setTypeUtilisateur(Utilisateur.TypeUtilisateur.PROFESSEUR);
    }

    void checkEmail(String email) throws EmailMalFormeException {
        if (email == null || email.isEmpty()) {
            throw new EmailMalFormeException();
        }
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        if (!pattern.matcher(email).matches()) {
            throw new EmailMalFormeException();
        }
    }
    @Override
    public String inscriptionProfesseur(String email, String pseudo) throws EmailMalFormeException, PseudoManquantException {

        checkEmail(email);
        if (pseudo == null || pseudo.isBlank()) {
            throw new PseudoManquantException();
        }

        Professeur professeur = new Professeur(email, pseudo, UUID.randomUUID().toString());
        this.professeursAValider.put(professeur.getCleAuthentification(),professeur);
        return professeur.getCleAuthentification();
    }


    private Professeur getProfesseur(String cleAuthentification) throws UtilisateurInexistantException, OperationNonAutoriseeException, CompteNonConfirmeException {
        Utilisateur utilisateur = getUtilisateur(cleAuthentification);
        try {
            Professeur professeur = (Professeur) utilisateur;
            if (professeur.getTypeUtilisateur() == Utilisateur.TypeUtilisateur.PROFESSEUR_NON_VERIFIE)
                throw new CompteNonConfirmeException();
            return professeur;
        }
        catch (ClassCastException e) {
            throw new OperationNonAutoriseeException();
        }

    }


    private Etudiant getEtudiant(String cleAuthentification) throws UtilisateurInexistantException, OperationNonAutoriseeException {
        Utilisateur utilisateur = getUtilisateur(cleAuthentification);
        try {
            Etudiant etudiant = (Etudiant) utilisateur;

            return etudiant;
        }
        catch (ClassCastException e) {
            throw new OperationNonAutoriseeException();
        }
    }

    @Override
    public Collection<QCM> getQCMsAValider(String cleUtilisateur) throws CompteNonConfirmeException, OperationNonAutoriseeException, UtilisateurInexistantException {

        getProfesseur(cleUtilisateur) ;
        return this.qcmsAValider;
    }

    @Override
    public void validerQCM(String cleUtilisateur, String cleQCM) throws QCMInexistantException, CompteNonConfirmeException, OperationNonAutoriseeException, UtilisateurInexistantException {
        Professeur professeur = getProfesseur(cleUtilisateur);
        Optional<QCM> qcm = qcmsAValider.stream().filter(x -> x.getCleQCM().equals(cleQCM)).findFirst();
        if (qcm.isEmpty())
            throw new QCMInexistantException();
        else {
            QCM qcmValide = qcm.get();
            qcmValide.valider();
            qcms.add(qcmValide);
            qcmsAValider.remove(qcmValide);
        }
        //TODO : valider le QCM si l'utilisateur est un professeur
    }

    @Override
    public void refuserQCM(String cleUtilisateur, String cleQCM) throws CompteNonConfirmeException, OperationNonAutoriseeException, UtilisateurInexistantException, QCMInexistantException {
        getProfesseur(cleUtilisateur);// permet de vérifier que c'est un professeur confirmé qui demande le refus du QCM
        Optional<QCM> qcm = qcmsAValider.stream().filter(x -> x.getCleQCM().equals(cleQCM)).findFirst();
        if (qcm.isEmpty())
            throw new QCMInexistantException();
        else {
            QCM qcmInvalide = qcm.get();
            qcmsAValider.remove(qcmInvalide);
        }
    }



    private Utilisateur getUtilisateur(String cleUtilisateur) throws UtilisateurInexistantException {
        Utilisateur utilisateur = utilisateurs.get(cleUtilisateur);
        if (Objects.isNull(utilisateur))
            throw new UtilisateurInexistantException();
        return utilisateur;
    }
    @Override
    public Collection<Utilisateur> getUtilisateursAValider(String cleUtilisateur) throws OperationNonAutoriseeException, CompteNonConfirmeException, UtilisateurInexistantException {
        getProfesseur(cleUtilisateur);
        return professeursAValider.values().stream().collect(Collectors.toList());
    }

    @Override
    public void validerUtilisateur(String cleUtilisateur, String cleUtilisateurAValider) throws OperationNonAutoriseeException, CompteNonConfirmeException, UtilisateurInexistantException {
        getProfesseur(cleUtilisateur)  ;
        Utilisateur utilisateurAValider = professeursAValider.get(cleUtilisateurAValider);
        if (Objects.isNull(utilisateurAValider))
            throw new UtilisateurInexistantException();

        utilisateurAValider.setTypeUtilisateur(Utilisateur.TypeUtilisateur.PROFESSEUR);
        professeursAValider.remove(cleUtilisateurAValider);
        utilisateurs.put(utilisateurAValider.getCleAuthentification(),utilisateurAValider);
    }

    @Override
    public void refuserUtilisateur(String cleUtilisateur, String cleUtilisateurAValider) throws OperationNonAutoriseeException, CompteNonConfirmeException, UtilisateurInexistantException {
        getProfesseur(cleUtilisateur)  ;
        Utilisateur utilisateurAValider = professeursAValider.get(cleUtilisateurAValider);
        if (Objects.isNull(utilisateurAValider))
            throw new UtilisateurInexistantException();
        professeursAValider.remove(cleUtilisateurAValider);
    }

    @Override
    public String inscription(String email, String pseudo) throws EmailMalFormeException, PseudoManquantException {
        checkEmail(email);
        if (Objects.isNull(pseudo) || pseudo.isBlank())
            throw new PseudoManquantException();

        Etudiant etudiant = new Etudiant(email, pseudo,UUID.randomUUID().toString());
        utilisateurs.put(etudiant.getCleAuthentification(), etudiant);
        return etudiant.getCleAuthentification();
    }

    @Override
    public String creerQCM(String cleEtudiant, String description, long tempsDisponible, Question[] questions) throws UtilisateurInexistantException, OperationNonAutoriseeException, InformationManquanteException, TempsIncoherentException, QuestionsIncoherentesException, CompteNonConfirmeException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        if (tempsDisponible < 0) throw new TempsIncoherentException();
        if (questions.length < 2) throw new QuestionsIncoherentesException();
        QCM qcm = new QCM(etudiant, description, tempsDisponible, questions);
        qcmsAValider.add(qcm);

        return qcm.getCleQCM();
    }

    @Override
    public Collection<QCM> getMesQCMsEnAttenteDeValidation(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        return qcmsAValider.stream().filter(x -> x.getCreateur().equals(etudiant)).collect(Collectors.toList());

    }

    @Override
    public Collection<QCM> getMesQCMsTermines(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException {
        Etudiant etudiant = getEtudiant(cleEtudiant);

        return qcms.stream().filter(x -> x.getCreateur().equals(etudiant)).filter(x -> x.getDateQCM().plusSeconds(x.getTempsDisponibleSecondes()).isBefore(LocalDateTime.now())).collect(Collectors.toList());
    }

    @Override
    public Collection<QCM> getMesQCMsPretsAEtrePublies(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        return qcms.stream().filter(x -> x.getCreateur().equals(etudiant)).filter(x -> x.estValide()).
                filter(x -> !x.estPublie()).
                collect(Collectors.toList());
    }

    @Override
    public void publierQCM(String cleEtudiant, String cleQCM) throws UtilisateurInexistantException, OperationNonAutoriseeException, QCMInexistantException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        QCM qcm = getQCM(cleQCM);
        if (etudiant.equals(qcm.getCreateur()))
            qcm.publierQCM();
        else
            throw new OperationNonAutoriseeException();

    }

    private QCM getQCM(String cleQCM) throws QCMInexistantException {
        Optional<QCM> qcm = qcms.stream().filter(x -> x.getCleQCM().equals(cleQCM)).findFirst();
        if (qcm.isEmpty())
            throw new QCMInexistantException();
        return qcm.get();

    }

    @Override
    public void inscriptionQCM(String cleEtudiant, String cleQCM) throws UtilisateurInexistantException, OperationNonAutoriseeException, QCMInexistantException, QCMDejaInscritException, QCMTermineException, ParticipationInterditePourCreateurException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        QCM qcm = getQCM(cleQCM);
        if (etudiant.equals(qcm.getCreateur()))
            throw new ParticipationInterditePourCreateurException();
        qcm.inscription(etudiant);
    }

    @Override
    public void repondreQCM(String cleEtudiant, String cleQCM, int idQuestion, int idReponse) throws QCMInexistantException, ReponseIncorrecteException, UtilisateurInexistantException, OperationNonAutoriseeException, EtudiantNonInscritException, QCMTermineException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        QCM qcm = getQCM(cleQCM);
        qcm.repondre(etudiant, idQuestion,idReponse);
    }


    @Override
    public void calculerResultat(String cleEtudiant, String cleQCM) throws QCMInexistantException, UtilisateurInexistantException, OperationNonAutoriseeException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        QCM qcm = getQCM(cleQCM);
        if (etudiant.equals(qcm.getCreateur()))
            qcm.calculerResultat();
        else
            throw new OperationNonAutoriseeException();
    }

    @Override
    public ResultatQCM getResultat(String cleEtudiant, String cleQCM) throws UtilisateurInexistantException, OperationNonAutoriseeException, EtudiantNonInscritException, QCMInexistantException, ResultatsNonCalculesException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        QCM qcm = getQCM(cleQCM);
        if (qcm.getParticipants().contains(etudiant) || qcm.getCreateur().equals(etudiant))
            return qcm.getResultat();

        throw new EtudiantNonInscritException();

    }

    @Override
    public Collection<QCMDTO> getQCMsPretsPourCompetitions(String cleEtudiant) throws OperationNonAutoriseeException, UtilisateurInexistantException {
        getEtudiant(cleEtudiant);
        return qcms.stream().filter(x -> x.estPublie()).map(x -> new QCMDTO(x)).collect(Collectors.toList());
    }

    @Override
    public QCMDTO getQCM(String cleEtudiant, String cleQCM) throws OperationNonAutoriseeException, UtilisateurInexistantException, QCMInexistantException {
        getEtudiant(cleEtudiant);
        QCM qcm = getQCM(cleQCM);
        return new QCMDTO(qcm);
    }

    @Override
    public Collection<Score> getMesScores(String cleEtudiant) throws UtilisateurInexistantException, OperationNonAutoriseeException {
        Etudiant etudiant = getEtudiant(cleEtudiant);
        return etudiant.getScores().stream().collect(Collectors.toList());
    }


    @Override
    public Utilisateur getUtilisateur(String email, String cleUtilisateur) throws MauvaisIdentifiantsException {
        Utilisateur utilisateur = utilisateurs.get(cleUtilisateur);
        if (utilisateur == null)
            throw new MauvaisIdentifiantsException();
        if (!utilisateur.getEmail().equals(email))
            throw new MauvaisIdentifiantsException();
        return utilisateur;
    }
}

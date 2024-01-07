package facade;

import exceptions.*;
import modele.QCM;
import modele.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestFacadeImpl {
    private FacadeImpl facade;
    private final String cleAdmin = "admin";

    @BeforeEach
    public void setUp() {
        facade = new FacadeImpl();
    }

    @Test
    public void testInscriptionProfesseur() throws EmailMalFormeException, PseudoManquantException, CompteNonConfirmeException, OperationNonAutoriseeException, UtilisateurInexistantException {
        String email = "test@example.com";
        String pseudo = "testPseudo";

        String cleAuthentification = facade.inscriptionProfesseur(email, pseudo);

        assertNotNull(cleAuthentification);
        assertTrue(facade.professeursAValider.containsKey(cleAuthentification));

        facade.validerUtilisateur(cleAdmin, cleAuthentification);

        assertFalse(facade.professeursAValider.containsKey(cleAuthentification));

    }

    @Test
    public void testInscriptionProfesseurWithInvalidEmail() {
        String email = "invalidEmail";
        String pseudo = "testPseudo";

        assertThrows(EmailMalFormeException.class, () -> facade.inscriptionProfesseur(email, pseudo));
    }

    @Test
    public void testInscriptionProfesseurWithMissingPseudo() {
        String email = "test@example.com";
        String pseudo = "";

        assertThrows(PseudoManquantException.class, () -> facade.inscriptionProfesseur(email, pseudo));
    }

    @Test
    public void testGetQCMsAValider() {
        String cleUtilisateur = UUID.randomUUID().toString();

        Collection<QCM> qcmsAValider = facade.getQCMsAValider(cleUtilisateur);

        assertNotNull(qcmsAValider);
        assertEquals(0, qcmsAValider.size());
    }



}

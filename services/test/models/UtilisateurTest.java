package models;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

/**
 * @author kbenhdech
 */
public class UtilisateurTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void createAndRetrieveUtilisateur() {
		new Utilisateur("karim.benhdech@gmx.fr", "kbenhdech", "motDePasse",
				"Karim BENHDECH");
		try {
			new Utilisateur("karim.benhdech@gmx.fr", "karim.benhdech",
					"motDePasse", "Karim BENHDECH");
			fail();
		} catch (PersistenceException pe) {
		}
		try {
			new Utilisateur("karim.benhdech@gmail.com", "kbenhdech",
					"motDePasse", "Karim BENHDECH");
			fail();
		} catch (PersistenceException pe) {
		}

		Utilisateur kbenhdech = Utilisateur.find("byCourriel",
				"karim.benhdech@gmx.fr").first();

		assertEquals(1, Utilisateur.find("byCourriel", "karim.benhdech@gmx.fr")
				.<Utilisateur> fetch().size());
		assertEquals(1, Utilisateur.find("byPseudo", "kbenhdech")
				.<Utilisateur> fetch().size());
		assertNotNull(kbenhdech);
		assertEquals("Karim BENHDECH", kbenhdech.nomComplet);
		assertNotSame("karim.benhdech", kbenhdech.pseudo);
		assertNotSame("karim.benhdech@gmail.com", kbenhdech.courriel);
	}

	// La vérification n'est pas à faire ici mais dans le Controller qui créera
	// les utilisateurs
	/*
	 * @Test public void createUtilisateurAndTestValidation (){ //Tentative de
	 * création de deux utilisateurs qui devrait échouer //L'courriel doit être
	 * valide //Le pseudo ne doit pas contenir d'arobase new
	 * Utilisateur("karim.benhdech", "kbenhdech", "motDePasse",
	 * "Karim BENHDECH"); new Utilisateur("karim.benhdech@gmx.fr",
	 * "kbenhdech@gmx.fr", "motDePasse", "Karim BENHDECH");
	 * 
	 * assertEquals(0, Utilisateur.find("byEmail",
	 * "karim.benhdech@gmx.fr").<Utilisateur>fetch().size()); assertEquals(0,
	 * Utilisateur.find("byLogin",
	 * "kbenhdech@gmx.fr").<Utilisateur>fetch().size()); }
	 */

	@Test
	public void tryConnectAsUtilisateur() {
		new Utilisateur("karim.benhdech@gmx.fr", "kbenhdech", "motDePasse",
				"Karim BENHDECH");

		assertNotNull(Utilisateur
				.connect("karim.benhdech@gmx.fr", "motDePasse"));
		assertNotNull(Utilisateur.connect("kbenhdech", "motDePasse"));
		assertNull(Utilisateur.connect("karim.benhdech@gmx.fr", "badPassword"));
		assertNull(Utilisateur.connect("bilbo@gmail.com", "motDePasse"));
	}

}

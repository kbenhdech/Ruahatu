package models;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

/**
 * @author kbenhdech
 */
public class AquariumTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void creationAquariumAvecUtilisateurNull() {
		try {
			new Aquarium("monPremierAquarium", 35.0, 10, 20., 7., 2.,
					"commentaire", null); // test l'unicité
			fail();
		} catch (PersistenceException pe) {
		}
	}

	@Test
	public void creationAquariumAvecPseudoNull() {
		try {
			Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
					"kbenhdech", "motDePasse", "Karim BENHDECH");
			new Aquarium(null, 35.0, 10, 20., 7., 2., "commentaire", kbenhdech)
					; // test l'unicité
			fail();
		} catch (PersistenceException pe) {
		}
	}

	@Test
	public void creationAquarium() {
		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		new Aquarium("monPremierAquarium", 35.0, 10, 20., 7., 2.,
				"commentaire", kbenhdech);
		new Aquarium("monPremierAquarium", 35.0, 10, 20., 7., 2.,
				"commentaire", kbenhdech);

		assertEquals(2, Aquarium.find("byNom", "monPremierAquarium")
				.<Aquarium> fetch().size());
		assertEquals(2, Aquarium.find("byNomAndUtilisateur",
				"monPremierAquarium", kbenhdech).<Aquarium> fetch().size());
	}

	@Test
	public void ajoutAquaPoissonEtVerifieRelationAquaPoisson() {
		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		Aquarium aquarium1 = new Aquarium("monPremierAquarium", 35.0, 10, 20.,
				7., 2., "commentaire", kbenhdech);
		Aquarium aquarium2 = new Aquarium("monSecondAquarium", 35.0, 10, 20.,
				7., 2., "commentaire", kbenhdech);

		AquaPoisson aquaPoisson1 = new AquaPoisson("Jerem1", new Date(),
				new Date(), "commentaire", kbenhdech, null, null);
		AquaPoisson aquaPoisson2 = new AquaPoisson("Jerem2", new Date(),
				new Date(), "commentaire", kbenhdech, null, null);
		AquaPoisson aquaPoisson3 = new AquaPoisson("Jerem3", new Date(),
				new Date(), "commentaire", kbenhdech, null, null);

		aquarium1.ajoutAquaPoisson(aquaPoisson1);
		aquarium1.ajoutAquaPoisson(aquaPoisson2);
		aquarium2.ajoutAquaPoisson(aquaPoisson3);

		assertEquals(3, AquaPoisson.find("byUtilisateur", kbenhdech)
				.<AquaPoisson> fetch().size());
		assertEquals(2, Aquarium.find("byUtilisateur", kbenhdech)
				.<Aquarium> fetch().size());
		assertEquals(2, Aquarium.find("byNom", "monPremierAquarium")
				.<Aquarium> first().aquaPoissons.size());
		assertEquals(1, Aquarium.find("byNom", "monSecondAquarium")
				.<Aquarium> first().aquaPoissons.size());
		assertEquals(aquarium1, AquaPoisson.find("byPseudo", "Jerem1")
				.<AquaPoisson> first().aquarium);
		assertEquals(aquarium1, AquaPoisson.find("byPseudo", "Jerem2")
				.<AquaPoisson> first().aquarium);
		assertEquals(aquarium2, AquaPoisson.find("byPseudo", "Jerem3")
				.<AquaPoisson> first().aquarium);

		aquarium2.ajoutAquaPoisson(aquaPoisson1);

		assertEquals(3, AquaPoisson.find("byUtilisateur", kbenhdech)
				.<AquaPoisson> fetch().size());
		assertEquals(2, Aquarium.find("byUtilisateur", kbenhdech)
				.<Aquarium> fetch().size());
		assertEquals(1, Aquarium.find("byNom", "monPremierAquarium")
				.<Aquarium> first().aquaPoissons.size());
		assertEquals(2, Aquarium.find("byNom", "monSecondAquarium")
				.<Aquarium> first().aquaPoissons.size());
		assertEquals(aquarium2, AquaPoisson.find("byPseudo", "Jerem1")
				.<AquaPoisson> first().aquarium);
		assertEquals(aquarium1, AquaPoisson.find("byPseudo", "Jerem2")
				.<AquaPoisson> first().aquarium);
		assertEquals(aquarium2, AquaPoisson.find("byPseudo", "Jerem3")
				.<AquaPoisson> first().aquarium);
	}

	@Test
	public void relationUtilisateur() {
		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		new Aquarium("monPremierAquarium", 35.0, 10, 20., 7., 2.,
				"commentaire", kbenhdech);

		assertEquals(1, Utilisateur.find("byPseudo", "kbenhdech")
				.<Utilisateur> first().aquariums.size());
	}
}

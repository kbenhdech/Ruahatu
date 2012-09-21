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
public class AquaPoissonTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void creationAquaPoissonAvecAquariumUtilisateurEtAquaPoissonUtilisateurDifferent() {
		try {
			Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
					"kbenhdech", "motDePasse", "Karim BENHDECH");
			Utilisateur abenhdech = new Utilisateur("asmaa.benhdech@gmx.fr",
					"abenhdech", "motDePasse", "Asmaa BENHDECH");
			Aquarium aquarium = new Aquarium("nom", 35., 10, 36., 5., 1.,
					"commentaire", kbenhdech);
			new AquaPoisson(null, new Date(), new Date(), "commentaire",
					abenhdech, null, aquarium);
			fail();
		} catch (PersistenceException p) {
		}
	}
	
	@Test
	public void creationAquaPoissonAvecUtilisateurNull() {
		try {
			new AquaPoisson("monPoisson", new Date(), new Date(),
					"commentaire", null, null, null);
			fail();
		} catch (PersistenceException p) {
		}
	}
	
	@Test
	public void creationAquaPoissonAvecPseudoNull() {
		try {
			new AquaPoisson(null, new Date(), new Date(), "commentaire",
					new Utilisateur("", "", "", ""), null, null);
			fail();
		} catch (PersistenceException p) {
		}
	}

	@Test
	public void recherchePoissonEtUtilisateurParAquaPoisson() {
		Poisson arenctus = new Poisson("Arenctus", "nomCommun", 2.0, 2.0, 2.0,
				2.0, 2.0, 2.0, 2.0, 2.0, "zoneDeVie",
				"informationsComplementaire", null, null);

		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		new AquaPoisson("Walter", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);

		assertEquals("Arenctus", AquaPoisson.find("byPseudo", "Walter")
				.<AquaPoisson> first().poisson.nomScientifique);
		assertEquals("kbenhdech", AquaPoisson.find("byPseudo", "Walter")
				.<AquaPoisson> first().utilisateur.pseudo);
	}

	@Test
	public void poissonAvecPlusieursAquaPoisson() {
		Poisson arenctus = new Poisson("Arenctus", "nomCommun", 2.0, 2.0, 2.0,
				2.0, 2.0, 2.0, 2.0, 2.0, "zoneDeVie",
				"informationsComplementaire", null, null);

		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		new AquaPoisson("Walter", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);
		new AquaPoisson("Mouf", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);
		new AquaPoisson("Goldrig", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);

		assertEquals("Arenctus", AquaPoisson.find("byPseudo", "Walter")
				.<AquaPoisson> first().poisson.nomScientifique);
		assertEquals("Arenctus", AquaPoisson.find("byPseudo", "Mouf")
				.<AquaPoisson> first().poisson.nomScientifique);
		assertEquals("Arenctus", AquaPoisson.find("byPseudo", "Goldrig")
				.<AquaPoisson> first().poisson.nomScientifique);
	}

	@Test
	public void utilisateurAvecPlusieursAquaPoisson() {
		Poisson arenctus = new Poisson("Arenctus", "nomCommun", 2.0, 2.0, 2.0,
				2.0, 2.0, 2.0, 2.0, 2.0, "zoneDeVie",
				"informationsComplementaire", null, null);

		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		new AquaPoisson("Walter", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);
		new AquaPoisson("Mouf", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);
		new AquaPoisson("Goldrig", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);

		assertEquals("kbenhdech", AquaPoisson.find("byPseudo", "Walter")
				.<AquaPoisson> first().utilisateur.pseudo);
		assertEquals("kbenhdech", AquaPoisson.find("byPseudo", "Mouf")
				.<AquaPoisson> first().utilisateur.pseudo);
		assertEquals("kbenhdech", AquaPoisson.find("byPseudo", "Goldrig")
				.<AquaPoisson> first().utilisateur.pseudo);
	}
	
	@Test
	public void relationUtilisateur() {
		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		new AquaPoisson("Walter", new Date(), new Date(), "commentaire",
				kbenhdech, null, null);

		assertEquals("kbenhdech", AquaPoisson.find("byPseudo", "Walter")
				.<AquaPoisson> first().utilisateur.pseudo);
		assertEquals("Walter", ((AquaPoisson)Utilisateur.find("byPseudo", "kbenhdech")
				.<Utilisateur> first().aquaPoissons.get(0)).pseudo);
	}
	
	@Test
	public void relationPoisson() {
		Poisson arenctus = new Poisson("Arenctus", "nomCommun", 2.0, 2.0, 2.0,
				2.0, 2.0, 2.0, 2.0, 2.0, "zoneDeVie",
				"informationsComplementaire", null, null);
		
		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");

		new AquaPoisson("Walter", new Date(), new Date(), "commentaire",
				kbenhdech, arenctus, null);

		assertEquals("kbenhdech", AquaPoisson.find("byPseudo", "Walter")
				.<AquaPoisson> first().utilisateur.pseudo);
		assertEquals("Walter", ((AquaPoisson)Poisson.find("byNomScientifique", "Arenctus")
				.<Poisson> first().aquaPoissons.get(0)).pseudo);
	}
	
	@Test
	public void relationAquarium() {
		Utilisateur kbenhdech = new Utilisateur("karim.benhdech@gmx.fr",
				"kbenhdech", "motDePasse", "Karim BENHDECH");
		
		Aquarium aquarium = new Aquarium("monPremierAquarium", 35., 10, 36., 5., 1.,
				"commentaire", kbenhdech);

		new AquaPoisson("Walter", new Date(), new Date(), "commentaire",
				kbenhdech, null, aquarium);

		assertEquals("kbenhdech", AquaPoisson.find("byPseudo", "Walter")
				.<AquaPoisson> first().utilisateur.pseudo);
		assertEquals("Walter", ((AquaPoisson)Aquarium.find("byNom", "monPremierAquarium")
				.<Aquarium> first().aquaPoissons.get(0)).pseudo);
	}
}

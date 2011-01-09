package models;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

/**
 * @author kbenhdech
 */
public class FamillePoissonTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void creationFamillePoissonAvecPseudoNull() {
		try {
			new FamillePoisson(null); 
			fail();
		} catch (PersistenceException pe) {
		}
	}

	@Test
	public void creationFamillePoisson() {
		FamillePoisson famillePoisson = new FamillePoisson("Abaratus");

		new Poisson("Ablactaurus", "Ab", 5.2, 6.4, 20., 26., 1., 2., 15., 20.,
				"Vie en zone tropicale", "", famillePoisson, null);
		new Poisson("Ablactaurus Strotuc", "Ab", 5.2, 6.4, 20., 26., 1., 2.,
				15., 20., "Vie en zone tropicale", "", famillePoisson, null)
				;

		assertEquals(2, FamillePoisson.find("byNom", "Abaratus")
				.<FamillePoisson> first().poissons.size());
		assertEquals(2, famillePoisson.poissons.size());
	}

	@Test
	public void relationPoisson() {
		FamillePoisson famillePoisson = new FamillePoisson("Abaratus");
		Poisson poisson = new Poisson("Ablactaurus", "Ab", 5.2, 6.4, 20., 26.,
				1., 2., 15., 20., "Vie en zone tropicale", "", famillePoisson,
				null);
		assertEquals("Abaratus", Poisson.find("byNomScientifique",
				"Ablactaurus").<Poisson> first().famillePoisson.nom);

		FamillePoisson famillePoisson2 = new FamillePoisson("Foo");
		famillePoisson2.ajoutPoisson(poisson);
		assertEquals("Foo", Poisson.find("byNomScientifique", "Ablactaurus")
				.<Poisson> first().famillePoisson.nom);
	}
}

package models;

import java.util.Arrays;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

/**
 * @author kbenhdech
 */
public class PaysTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void creationPays() {
		new Pays("France");
		new Pays("Italie");
		try {
			new Pays("France"); // test l'unicité
			fail();
		} catch (PersistenceException pe) {
		}
		assertEquals(1, Pays.find("byNom", "France").<Pays> fetch().size());
		assertEquals(1, Pays.find("byNom", "Italie").<Pays> fetch().size());
	}

	@Test
	public void recherchePoissonsParPays() {
		Pays france = new Pays("France");
		Pays italie = new Pays("Italie");
		Pays espagne = new Pays("Espagne");

		new Poisson("john", "nomCommun", 10.5, 12.5, 20., 25., 7., 8., 1., 2.,
				"zoneDeVie", "informationsComplementaire", null, Arrays.asList(
						france, italie, espagne));
		new Poisson("brad", "nomCommun", 10.5, 12.5, 20., 25., 7., 8., 1., 2.,
				"zoneDeVie", "informationsComplementaire", null, Arrays
						.asList(france));
		new Poisson("britney", "nomCommun", 10.5, 12.5, 20., 25., 7., 8., 1.,
				2., "zoneDeVie", "informationsComplementaire", null, Arrays
						.asList(italie));

		assertEquals(2, Pays.find("byNom", "France").<Pays> first().poissons
				.size());
		assertEquals(2, Pays.find("byNom", "Italie").<Pays> first().poissons
				.size());
		assertEquals(1, Pays.find("byNom", "Espagne").<Pays> first().poissons
				.size());
	}
}

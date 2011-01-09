package models;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

/**
 * @author kbenhdech
 */
public class PoissonTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void creationPoisson() {
		new Poisson("Arenctus", "nomCommun", 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
				2.0, "zoneDeVie", "informationsComplementaire", null, null);

		assertEquals(1, Poisson.find("byNomScientifique", "Arenctus").fetch()
				.size());
	}

	@Test
	public void creationPoissonAvecFamilleEtPays() {
		new Poisson("Arenctus", "nomCommun", 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0,
				2.0, "zoneDeVie", "informationsComplementaire",
				new FamillePoisson("Foobar"), Arrays.asList(new Pays("Brésil")));

		assertEquals(1, Poisson.find("byNomScientifique", "Arenctus").fetch()
				.size());
		assertEquals(1, FamillePoisson.find("byNom", "Foobar")
				.<FamillePoisson> first().poissons.size());
		assertEquals(1, Pays.find("byNom", "Brésil").<Pays> first().poissons
				.size());
	}

}

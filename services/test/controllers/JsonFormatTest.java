package controllers;

import org.junit.Test;

import play.mvc.Before;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * @author kbenhdech
 */
public class JsonFormatTest extends FunctionalTest {

	String autorisationKbenhdechArgs = "?pseudo=kbenhdech&cle=8fa4d6829940fdf1a97c6cd6d12614a010bfcdde";
	String authentificationKbenhdechArgs = "?pseudo=kbenhdech&motDePasse=kb21";
	
	@Test
	public void authentificationWebServicesReturnsAreJson() {
		assertTrue(isJson("/authentification", authentificationKbenhdechArgs));
	}

	@Test
	public void paysWebServicesReturnsAreJson() {
		assertTrue(isJson("/pays", autorisationKbenhdechArgs));
		assertTrue(isJson("/pays/1", autorisationKbenhdechArgs));
		assertTrue(isJson("/pays/2", autorisationKbenhdechArgs));
	}

	@Test
	public void poissonWebServicesReturnsAreJson() {
		assertTrue(isJson("/poissons", autorisationKbenhdechArgs));
		assertTrue(isJson("/poisson/1", autorisationKbenhdechArgs));
		assertTrue(isJson("/poisson/2", autorisationKbenhdechArgs));
		assertTrue(isJson("/poisson/familles", autorisationKbenhdechArgs));
		assertTrue(isJson("/poisson/famille/1", autorisationKbenhdechArgs));
		assertTrue(isJson("/poisson/famille/2", autorisationKbenhdechArgs));
		assertTrue(isJson("/poisson/famille/1/poissons", autorisationKbenhdechArgs));
		assertTrue(isJson("/poisson/famille/2/poissons", autorisationKbenhdechArgs));
	}

	@Test
	public void utilisateurWebServicesReturnsAreJson() {
		// assertTrue(isJson("/utilisateurs")); //FIXME
		assertTrue(isJson("/utilisateur/kbenhdech", autorisationKbenhdechArgs));
		assertTrue(isJson("/utilisateur/kbenhdech/poissons", autorisationKbenhdechArgs));
		isNotFound("/utilisateur/kbenhdech/poisson/1", autorisationKbenhdechArgs);
		assertTrue(isJson("/utilisateur/kbenhdech/poisson/4", autorisationKbenhdechArgs));
		assertTrue(isJson("/utilisateur/kbenhdech/aquariums", autorisationKbenhdechArgs));
		isNotFound("/utilisateur/kbenhdech/aquarium/1", autorisationKbenhdechArgs);
		assertTrue(isJson("/utilisateur/kbenhdech/aquarium/3", autorisationKbenhdechArgs));
		assertTrue(isJson("/utilisateur/kbenhdech/aquarium/3/poissons", autorisationKbenhdechArgs));
	}

	private Boolean isJson(String url, String args) {
		Response response = GET(url
				+args);
		assertIsOk(response);
		assertContentType("application/json", response);
		try {
			new JsonParser().parse(response.out.toString());
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}
	
	private void isNotFound(String url, String args) {
		Response response = GET(url
				+args);
		assertIsNotFound(response);
	}
}
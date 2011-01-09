package controllers;

import java.util.List;

import models.FamillePoisson;
import play.mvc.Controller;

/**
 * @author kbenhdech
 * 
 *         /poissons <br />
 *         /poisson/{id} <br />
 *         
 *         /poisson/familles <br />
 *         /poisson/famille/{id} <br />
 *         /poisson/famille/{id}/poissons <br />
 */
public class Poisson extends Controller {

	public static void poissons() {
		List<models.Poisson> poissons = models.Poisson.findAll();
		if(poissons != null) render(poissons); else notFound();
	}
	
	public static void poissonParId(Long id) {
		models.Poisson poisson = models.Poisson.find("byId", id).<models.Poisson>first();
		if(poisson != null) render(poisson); else notFound();
	}
	
	public static void familles() {
		List<FamillePoisson> famillePoissons = FamillePoisson.findAll();
		if(famillePoissons != null) render(famillePoissons); else notFound();
	}
	
	public static void familleParId(Long id) {
		FamillePoisson famillePoisson = FamillePoisson.find("byId", id).<FamillePoisson>first();	
		if(famillePoisson != null) render(famillePoisson); else notFound();
	}
	
	public static void poissonsDansFamille(Long id) {
		FamillePoisson famillePoisson = FamillePoisson.find("byId", id).<FamillePoisson>first();
		List<models.Poisson> poissons = null;
		if(famillePoisson != null) poissons = famillePoisson.poissons;
		if(poissons != null) render(poissons); else notFound();
	}

}
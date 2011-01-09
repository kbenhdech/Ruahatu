package controllers;

import java.util.List;

import play.mvc.Controller;

/**
 * @author kbenhdech
 * 
 *         /pays <br />
 *         /pays/{id} <br />
 */
public class Pays extends Controller {

	public static void pays() {
		List<models.Pays> pays = models.Pays.findAll();
		if(pays != null) render(pays); else notFound();
	}
	
	public static void paysParId(Long id) {
		models.Pays pays = models.Pays.findById(id);
		if(pays != null) render(pays); else notFound();
	}

}
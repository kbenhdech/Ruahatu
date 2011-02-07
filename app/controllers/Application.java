package controllers;

import play.data.validation.Email;
import play.data.validation.Error;
import play.data.validation.Password;
import play.data.validation.Required;
import play.mvc.Controller;

/**
 * @author kbenhdech
 * 
 */
public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void poissons() {
		render();
	}

	public static void poisson(Integer id) {
		render(id);
	}

	public static void enregistrement() {
		render();
	}
    /*
    registration.error.pseudo.empty=Le pseudo doit être renseigné
    registration.error.pseudo.already.in.use=Le pseudo saisie est dèjà utilisé par un autre membre
    registration.error.email.empty=L'adresse email doit être renseigné
    registration.error.email.already.in.use=L'adresse email saisie est dèjà utilisé par un autre membre
    registration.error.email.not.valid=L'adresse email n'est pas valide
    registration.error.password.empty=Le mot de passe doit être rensigné
    registration.error.password.not.identical=Les mot de passes ne sont pas identiques*/
	public static void enregistrementForm(@Required @Email String courriel,
			@Required String pseudo, String nomComplet,
			@Required @Password String motDePasseString) throws Throwable {
		/*
		 * if(validation.hasErrors()) { flash.keep("url");
		 * flash.error("secure.error"); params.flash(); }
		 */
		if (validation.hasErrors()) {
			for (Error error : validation.errors()) {
				System.out.println(error.getKey() + " " + error.message());
				flash.error("Il y a une ou plusieurs erreurs");
				flash.put(error.getKey(), error.getKey() + " "
						+ error.message());
			}
		}

		// redirect(request.url);
		enregistrement();
	}
	
	public static void board() {
		render();
	}
	
	public static void myFishes() {
		render();
	}
	
	public static void myFish(Integer id) {
		render(id);
	}
	
	public static void myAquariums() {
		render();
	}
	
	public static void myAquarium(Integer id) {
		render(id);
	}

}
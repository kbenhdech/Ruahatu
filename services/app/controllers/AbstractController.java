package controllers;

import models.AccesServicesWeb;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.i18n.Messages;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * @author kbenhdech 
 * Gére les appels cross-domain à cette application sur les
 * services web. 
 * Gére les autorisations sur les services web.
 * 
 */
public abstract class AbstractController extends Controller {

	static String callback = null;

	@Before(priority = 1)
	public static void crossDomainCallback() {
		callback = params.get("callback");
		if (callback != null) {
			Logger.info(Messages.get("logger.info.cross.domain.call"));
			response.setHeader("Content-Type", "text/javascript");
			renderArgs.put("callback", callback);
		}
	}

	@Before(priority = 2)
	public static void IsAutorisedDomain() {
		AccesServicesWeb accesServicesWeb = models.AccesServicesWeb.find(
				"byDomaine", request.domain).first();
		if (accesServicesWeb == null) {
			Logger.info(Messages.get("logger.info.autorised.access"));
			notFound();
		}
	}

	@Before(priority = 3, unless = { "AbstractController.crossDomainCallback",
			"Authentification.authentificationParUtilisateurEtMotDePasse",
			"Poisson.poissons", "Poisson.familleParId", "Poisson.familles",
			"Poisson.poissonParId", "Poisson.poissonsDansFamille" })
	public static void crossDomainAutorisation() {
		callback = params.get("callback");
		String domaine = request.domain;
		String pseudo = params.get("pseudo");
		String cleCrypeRecu = params.get("cle");
		Logger.info(Messages.get("logger.info.autorisation.request.info",
				domaine, pseudo, cleCrypeRecu));

		models.Utilisateur utilisateur = models.Utilisateur.find("byPseudo",
				pseudo).first();
		AccesServicesWeb accesServicesWeb = models.AccesServicesWeb.find(
				"byDomaine", request.domain).first();

		Boolean requeteAutoriseePourCetteUtilisateur = requeteAutoriseePourCetteUtilisateur(
				utilisateur, accesServicesWeb, pseudo);
		Boolean requeteNonAutoriseePourToutUtilisateur = StringUtils.contains(
				request.path, "/utilisateurs");
		Boolean cleAutorisee = cleAutorisee(utilisateur, accesServicesWeb,
				cleCrypeRecu);

		if (!cleAutorisee || requeteNonAutoriseePourToutUtilisateur
				|| !requeteAutoriseePourCetteUtilisateur) {
			Logger.info(Messages.get("logger.info.autorised.access"));
			notFound();
		}

	}

	private static Boolean requeteAutoriseePourCetteUtilisateur(
			models.Utilisateur utilisateur, AccesServicesWeb accesServicesWeb,
			String pseudo) {
		Boolean requeteAutoriseePourCetteUtilisateur = false;
		if (utilisateur != null && accesServicesWeb != null) {

			Integer utilisateurNumberMatch = StringUtils.countMatches(
					request.path, "utilisateur");

			requeteAutoriseePourCetteUtilisateur = (utilisateurNumberMatch == 1 && StringUtils
					.contains(request.path, "/utilisateur/" + pseudo))
					|| utilisateurNumberMatch == 0;
		}
		Logger.info(Messages.get("logger.info.autorised.request",
				requeteAutoriseePourCetteUtilisateur));
		return requeteAutoriseePourCetteUtilisateur;
	}

	private static Boolean cleAutorisee(models.Utilisateur utilisateur,
			AccesServicesWeb accesServicesWeb, String cleCrypeRecu) {
		Boolean cleAutorisee = false;
		if (utilisateur != null && accesServicesWeb != null) {
			String cleCrypteCalcule = Crypto.sign(utilisateur.motDePasse
					+ accesServicesWeb.cle, accesServicesWeb.cle.getBytes());
			cleAutorisee = !StringUtils.isEmpty(cleCrypeRecu)
					&& !StringUtils.isEmpty(cleCrypteCalcule)
					&& cleCrypteCalcule.equals(cleCrypeRecu);
		}
		Logger.info(Messages.get("logger.info.autorised.key", cleAutorisee));
		return cleAutorisee;
	}
}

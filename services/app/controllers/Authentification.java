package controllers;

import play.Logger;
import play.i18n.Messages;

/**
 * @author kbenhdech
 * 
 */
public class Authentification extends AbstractController {

	public static void authentificationParUtilisateurEtMotDePasse() {
		String login = params.get("pseudo");
		String motDePasse = params.get("motDePasse");
		Logger.info(Messages.get("logger.info.authentification.info", login,
				motDePasse));

		models.Utilisateur utilisateur = models.Utilisateur.find(
				"byPseudoAndMotDePasse", login, motDePasse).first();
		if (utilisateur != null) {
			Logger.info(Messages.get("logger.info.authentification.success"));
			render(utilisateur);
		} else {
			Logger.info(Messages.get("logger.info.authentification.error"));
			notFound();
		}
	}
}

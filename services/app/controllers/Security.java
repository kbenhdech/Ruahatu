package controllers;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.i18n.Messages;
import enums.Session;

/**
 * @author kbenhdech 
 * Gére la sécurité lors d'un accés direct par le domaine où
 * est présente cette application.
 * 
 */
public class Security extends Secure.Security {

	static final String ADMINISTRATEUR = "ADMINISTRATEUR";
	static final String PROPRIETAIRE = "PROPRIETAIRE";

	static boolean authentify(String username, String password) {
		models.Utilisateur utilisateur = models.Utilisateur.find(
				"byPseudoAndMotDePasse", username, password).first();
		if (utilisateur != null) {
			session.put(Session.PSEUDO.name(), String
					.valueOf(utilisateur.pseudo));
			session.put(Session.ID.name(), String.valueOf(utilisateur.id));
			session.put(Session.ADMIN.name(), String
					.valueOf(utilisateur.estAdministrateur));
			return true;
		} else {
			Logger.info(Messages.get("logger.info.access.error", username,
					password));
			return false;
		}
	}

	static boolean check(String profile) {
		if (profile.equals(PROPRIETAIRE)) {
			return estAdministrateur() || estProprietaire();
		}
		if (profile.equals(ADMINISTRATEUR)) {
			return estAdministrateur();
		}
		return false;
	}

	private static boolean estAdministrateur() {
		return session.get(Session.ADMIN.name()).equals("true");
	}

	private static boolean estProprietaire() {
		return StringUtils.contains(request.path, "/"
				+ session.get(Session.PSEUDO.name()));
	}

	static void onDisconnected() {
		Application.index();
	}

	static void onAuthenticated() {
		redirect(request.url);
	}
}

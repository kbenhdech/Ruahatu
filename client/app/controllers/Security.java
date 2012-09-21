package controllers;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;
import play.i18n.Messages;
import play.libs.Crypto;
import beans.Utilisateur;

import com.google.gson.Gson;

import enums.Session;

/**
 * @author kbenhdech
 *
 */
public class Security extends Secure.Security {
	//FIXME Security module really useful ?

	static final String ADMINISTRATEUR = "ADMINISTRATEUR";
	static final String PROPRIETAIRE = "PROPRIETAIRE";

	static boolean authentify(String username, String password) {
		Utilisateur utilisateur = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(Play.configuration
				.getProperty("ws.host")
				+ "/authentification");
		try {
			method.setQueryString(URIUtil.encodeQuery("pseudo=" + username
					+ "&motDePasse=" + password));

			client.executeMethod(method);
			String response = method.getResponseBodyAsString();
			if (response != null && StringUtils.isNotEmpty(response)) {
				Gson gson = new Gson();
				System.out.println(response);
				utilisateur = gson.fromJson(response, Utilisateur.class);
			}

		} catch (URIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

		if (utilisateur != null) {
			session.put(Session.LOGIN.name(), String.valueOf(username));
			session.put(Session.ADMIN.name(), String
					.valueOf(utilisateur.estAdministrateur));
			session.put(Session.KEY.name(), Crypto.sign(password
					+ Play.configuration.getProperty("ws.key"),
					Play.configuration.getProperty("ws.key").getBytes()));
			return true;
		} else {
			Logger.info(Messages.get("logger.info.access.error", username,
					password));
			return false;
		}
	}

	static boolean check(String profile) {
		//FIXME
		return false;
	}

	static void onDisconnected() {
		Application.index();
	}

	static void onAuthenticated() {
		redirect(request.url);
	}
}

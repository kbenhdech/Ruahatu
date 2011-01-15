package controllers;

import java.util.List;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author kbenhdech
 * 
 *         /utilisateurs <br />
 *         /utilisateur/{pseudo} <br />
 * 
 *         /utilisateur/{pseudo}/poissons <br />
 *         /utilisateur/{pseudo}/poisson/{id} <br />
 * 
 *         /utilisateur/{pseudo}/aquariums <br />
 *         /utilisateur/{pseudo}/aquarium/{id} <br />
 *         /utilisateur/{pseudo}/aquarium/{id}/poissons
 */
public class Utilisateur extends AbstractController {

	public static void utilisateurs() {
		List<models.Utilisateur> utilisateurs = models.Utilisateur.findAll();
		if (utilisateurs != null)
			render(utilisateurs);
		else
			notFound();
	}

	public static void utilisateurParId(String pseudo) {
		models.Utilisateur utilisateur = models.Utilisateur.find("byPseudo", pseudo)
				.<models.Utilisateur> first();
		if (utilisateur != null)
			render(utilisateur);
		else
			notFound();
	}

	public static void poissonsUtilisateur(String pseudo) {
		models.Utilisateur utilisateur = models.Utilisateur.find("byPseudo", pseudo)
				.<models.Utilisateur> first();
		List<models.AquaPoisson> poissons = null;
		if (utilisateur != null)
			poissons = utilisateur.aquaPoissons;
		if (poissons != null)
			render(poissons);
		else
			notFound();
	}

	public static void poissonUtilisateurParId(String pseudo,
			Long idPoisson) {
		models.AquaPoisson poisson = models.AquaPoisson.find("byId", idPoisson)
				.<models.AquaPoisson> first();
		if (poisson.utilisateur == null
				|| !poisson.utilisateur.pseudo.equals(pseudo))
			poisson = null;
		if (poisson != null)
			render(poisson);
		else
			notFound();
	}

	public static void aquariumsUtilisateur(String pseudo) {
		models.Utilisateur utilisateur = models.Utilisateur.find("byPseudo", pseudo)
				.<models.Utilisateur> first();
		List<models.Aquarium> aquariums = null;
		if (utilisateur != null)
			aquariums = utilisateur.aquariums;
		if (aquariums != null)
			render(aquariums);
		else
			notFound();
	}

	public static void aquariumUtilisateurParId(String pseudo,
			Long idAquarium) {
		models.Aquarium aquarium = models.Aquarium.find("byId", idAquarium)
				.<models.Aquarium> first();
		if (aquarium == null || aquarium.utilisateur == null
				|| !aquarium.utilisateur.pseudo.equals(pseudo))
			aquarium = null;
		if (aquarium != null)
			render(aquarium);
		else
			notFound();
	}

	public static void poissonsAquariumUtilisateurParId(String pseudo,
			Long idAquarium) {
		models.Aquarium aquarium = models.Aquarium.find("byId", idAquarium)
				.<models.Aquarium> first();
		if (aquarium.utilisateur == null
				|| !aquarium.utilisateur.pseudo.equals(pseudo))
			aquarium = null;
		if (aquarium != null && aquarium.aquaPoissons != null) {
			List<models.AquaPoisson> poissons = aquarium.aquaPoissons;
			render(poissons);
		} else
			notFound();
	}

}
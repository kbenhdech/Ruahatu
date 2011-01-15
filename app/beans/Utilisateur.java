package beans;


/**
 * @author kbenhdech
 */
public class Utilisateur {

	public String courriel;
	public String pseudo;
	public String nomComplet;
	public boolean estAdministrateur;

	public Utilisateur(String courriel, String pseudo, String motDePasse,
			String nomComplet) {
		this.courriel = courriel;
		this.pseudo = pseudo;
		this.nomComplet = nomComplet;
		this.estAdministrateur = false;
	}
}

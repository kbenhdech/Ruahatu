package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

/**
 * @author kbenhdech
 */
@Entity
public class Utilisateur extends Model {

	@Column(unique = true, nullable = false)
	public String courriel;

	@Column(unique = true, nullable = false)
	public String pseudo;

	public String motDePasse;
	public String nomComplet;
	public boolean estAdministrateur;

	@OneToMany(mappedBy = "utilisateur")
	public List<AquaPoisson> aquaPoissons;

	@OneToMany(mappedBy = "utilisateur")
	public List<Aquarium> aquariums;

	public Utilisateur(String courriel, String pseudo, String motDePasse,
			String nomComplet) {
		this.courriel = courriel;
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.nomComplet = nomComplet;
		this.estAdministrateur = false;
		this.aquaPoissons = new ArrayList<AquaPoisson>();
		this.aquariums = new ArrayList<Aquarium>();
		this.save();
	}

	public static Utilisateur connect(String CourrielOrPseudo, String motDePasse) {
		Utilisateur utilisateur = (Utilisateur) find("byCourrielAndMotDePasse",
				CourrielOrPseudo, motDePasse).first();
		if (utilisateur != null)
			return utilisateur;
		else
			return find("byPseudoAndMotDePasse", CourrielOrPseudo, motDePasse)
					.first();
	}

	public Utilisateur ajoutAquaPoisson(AquaPoisson aquapoisson) {
		if (aquapoisson == null)
			return null;

		aquapoisson.utilisateur = this;
		aquaPoissons.add(aquapoisson);
		return this;
	}

	public Utilisateur ajoutAquarium(Aquarium aquarium) {
		if (aquarium == null)
			return null;

		aquarium.utilisateur = this;
		aquariums.add(aquarium);
		return this;
	}
}

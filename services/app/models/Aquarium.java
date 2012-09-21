package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;

import play.db.jpa.Model;

/**
 * @author kbenhdech
 */
@Entity
public class Aquarium extends Model {

	@Column(nullable = false)
	public String nom;

	public Double volumeEau; // en cm cube
	public Integer puissanceEclairage;
	public Double temperatureEau; // en degré celcius
	public Double dureteEau;
	public Double aciditeEau;
	public String commentaire;

	@ManyToOne(optional = false)
	public Utilisateur utilisateur;

	@OneToMany(mappedBy = "aquarium")
	public List<AquaPoisson> aquaPoissons;

	public Aquarium(String nom, Double volumeEau, Integer puissanceEclairage,
			Double temperatureEau, Double aciditeEau, Double dureteEau,
			String commentaire, Utilisateur utilisateur) {
		this.nom = nom;
		this.volumeEau = volumeEau;
		this.puissanceEclairage = puissanceEclairage;
		this.temperatureEau = temperatureEau;
		this.aciditeEau = aciditeEau;
		this.dureteEau = dureteEau;
		this.commentaire = commentaire;
		aquaPoissons = new ArrayList<AquaPoisson>();

		this.utilisateur = utilisateur;
		if (utilisateur != null && utilisateur.aquariums != null) {
			utilisateur.aquariums.add(this);
		}
		this.save();
	}

	public Aquarium ajoutAquaPoisson(AquaPoisson aquaPoisson) {
		if (aquaPoisson == null)
			return null;
		if (aquaPoisson.utilisateur != null && this.utilisateur != null
				&& aquaPoisson.utilisateur.getId() != this.utilisateur.getId()) {
			throw new PersistenceException();
		}

		// Un aquaPoisson ne peut être dans deux aquarium (on le supprime du
		// premier)
		if (aquaPoisson.aquarium != null
				&& aquaPoisson.aquarium.aquaPoissons != null)
			aquaPoisson.aquarium.aquaPoissons.remove(aquaPoisson);
		aquaPoisson.aquarium = this;

		aquaPoissons.add(aquaPoisson);
		return this;
	}
}

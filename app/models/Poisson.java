package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

/**
 * @author kbenhdech
 */
@Entity
public class Poisson extends Model {

	@Column(unique = true, nullable = false)
	public String nomScientifique;

	@Column(nullable = false)
	public String nomCommun;

	public Double tailleAdulteMin; // En cm
	public Double tailleAdulteMax; // En cm

	public Double temperatureMin; // En degrés
	public Double temperatureMax; // En degrés

	public Double aciditeMin;
	public Double aciditeMax;

	public Double dureteMin;
	public Double dureteMax;

	public String zoneDeVie;

	public String informationsComplementaire;

	@ManyToOne
	public FamillePoisson famillePoisson;

	@ManyToMany
	public List<Pays> pays;

	@OneToMany(mappedBy = "poisson")
	public List<AquaPoisson> aquaPoissons;

	public Poisson(String nomScientifique, String nomCommun,
			Double tailleAdulteMin, Double tailleAdulteMax,
			Double temperatureMin, Double temperatureMax, Double aciditeMin,
			Double aciditeMax, Double dureteMin, Double dureteMax,
			String zoneDeVie, String informationsComplementaire,
			FamillePoisson famillePoisson, List<Pays> pays) {
		this.nomScientifique = nomScientifique;
		this.nomCommun = nomCommun;
		this.tailleAdulteMin = tailleAdulteMin;
		this.tailleAdulteMax = tailleAdulteMax;
		this.temperatureMin = temperatureMin;
		this.temperatureMax = temperatureMax;
		this.aciditeMin = aciditeMin;
		this.aciditeMax = aciditeMax;
		this.dureteMin = dureteMin;
		this.dureteMax = dureteMax;
		this.zoneDeVie = zoneDeVie;
		this.informationsComplementaire = informationsComplementaire;

		this.famillePoisson = famillePoisson;
		if (famillePoisson != null) {
			famillePoisson.poissons.add(this);
		}

		this.pays = new ArrayList<Pays>();
		if (pays != null && !pays.isEmpty()) {
			this.pays.addAll(pays);
			for (Pays onePays : pays) {
				onePays.poissons.add(this);
			}
		}

		this.aquaPoissons = new ArrayList<AquaPoisson>();
		
		this.save();
	}

	public Poisson ajoutAquaPoisson(AquaPoisson aquapoisson) {
		if (aquapoisson == null)
			return null;

		aquaPoissons.add(aquapoisson);

		if (aquapoisson.poisson != null
				&& aquapoisson.poisson.getId() != this.getId()) {
			aquapoisson.poisson = this;
		}

		return this;
	}
}

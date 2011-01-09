package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

/**
 * @author kbenhdech
 */
@Entity
public class FamillePoisson extends Model {

	//@Id @GeneratedValue long id; // still set automatically

	@Column(unique = true, nullable = false)
	public String nom;

	@OneToMany(mappedBy = "famillePoisson")
	public List<Poisson> poissons;

	public FamillePoisson(String nom) {
		this.poissons = new ArrayList<Poisson>();
		this.nom = nom;
		this.save();
	}

	public FamillePoisson ajoutPoisson(Poisson poisson) {
		if (poisson == null)
			return null;

		this.poissons.add(poisson);
		poisson.famillePoisson = this;
		
		return this;
	}
}

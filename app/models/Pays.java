package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.db.jpa.Model;

/**
 * @author kbenhdech
 */
@Entity
public class Pays extends Model {

	@Column(unique = true, nullable = false)
	public String nom;

	@ManyToMany(mappedBy = "pays")
	public List<Poisson> poissons;

	public Pays(String nom) {
		poissons = new ArrayList<Poisson>();
		this.nom = nom;
		this.save();
	}

}

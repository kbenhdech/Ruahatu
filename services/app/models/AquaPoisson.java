package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;

import play.db.jpa.Model;

/**
 * @author kbenhdech
 */
@Entity
public class AquaPoisson extends Model {

	@Column(nullable = false)
	public String pseudo;

	public Date dateAchat;
	public Date dateNaissance;

	@Lob
	public String commentaire;

	@ManyToOne
	public Poisson poisson;

	@ManyToOne(optional = false)
	public Utilisateur utilisateur;

	@ManyToOne
	public Aquarium aquarium;

	public AquaPoisson(String pseudo, Date dateAchat, Date dateNaissance,
			String commentaire, Utilisateur utilisateur, Poisson poisson,
			Aquarium aquarium) {
		this.pseudo = pseudo;
		this.dateAchat = dateAchat;
		this.dateNaissance = dateNaissance;
		this.commentaire = commentaire;

		this.utilisateur = utilisateur;
		if (utilisateur != null) {
			utilisateur.aquaPoissons.add(this);
		}

		this.poisson = poisson;
		if (poisson != null) {
			poisson.aquaPoissons.add(this);
		}

		this.aquarium = aquarium;
		if (aquarium != null) {
			if (utilisateur.getId() == aquarium.utilisateur.getId()) {
				aquarium.aquaPoissons.add(this);
			} else {
				throw new PersistenceException();
			}
		}
		
		this.save();
	}
}

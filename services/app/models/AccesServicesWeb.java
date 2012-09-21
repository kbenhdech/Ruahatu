package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author kbenhdech
 */
@Entity
public class AccesServicesWeb extends Model {

	@Column(nullable = false, unique=true)
	public String domaine;
	public String cle;
}

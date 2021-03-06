package models.atlas.fish

import play.api.Play.current
import beans.atlas.fish.AtlasFish
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import beans.types.{NOT_EXIST, ALREADY_EXIST, ApiErrorType}

/**
 * Classe de gestion  de la  persistance du type AtlasFish, un poisson de l'Atlas.
 * L'unicité est assurée au niveau du nom scientifique.
 *
 * @author Karim BENHDECH
 */
object AtlasFishModel extends Table[AtlasFish]("ATLAS_FISH") {

  // Définition des champs
  def id = column[Long]("ATLAS_FISH_ID", O.PrimaryKey, O.AutoInc)

  def scientificName = column[String]("SCIENTIFIC_NAME", O.NotNull)

  def commonName = column[String]("COMMON_NAME", O.NotNull)

  // Définition des indexes
  def idx = index("ATLAS_FISH_INDEX_NAME", (scientificName), unique = true)

  def * = id.? ~ scientificName ~ commonName <>(AtlasFish.apply _, AtlasFish.unapply _)

  def autoInc = * returning id

  /**
   * Définition des 'Finder'.
   */
  val byId = createFinderBy(_.id)
  val byScientificName = createFinderBy(_.scientificName)

  /**
   * Recherche d'un poisson dans l'Atlas par son nom ID.
   *
   * @param id Identifiant.
   * @return Un poisson de l'Atlas par son ID.
   */
  def findById(id: Long): Option[AtlasFish] = DB.withSession {
    implicit session =>
      AtlasFishModel.byId(id).firstOption
  }

  /**
   * Recherche d'un poisson dans l'Atlas par son nom sicentifique.
   *
   * @param scientificName Nom scientifique.
   * @return Un poisson de l'Atlas par son nom scientifique.
   */
  def findByScientificName(scientificName: String): Option[AtlasFish] = DB.withSession {
    implicit session =>
      AtlasFishModel.byScientificName(scientificName).firstOption
  }

  /**
   * @return Le nombre de poissons dans l'Atlas.
   */
  def count: Int = DB.withSession {
    implicit session =>
      Query(AtlasFishModel.length).first
  }

  /**
   * Insertion d'un poisson dans l'Atlas.
   *
   * @param atlasFish Un poisson de l'Atlas.
   * @return L'id du poisson de l'Atlas créé.
   */
  def insert(atlasFish: AtlasFish): Long = {
    DB.withSession {
      implicit session =>
        AtlasFishModel.autoInc.insert(atlasFish)
    }
  }

  /**
   * Suppression d'un poisson dans l'Atlas.
   *
   * @param scientificName Nom scientifique.
   * @return Une Option indiquant s'il y a eu erreur ou non.
   */
  def deleteByScientificName(scientificName: String): Option[ApiErrorType] = DB.withTransaction {
    implicit session =>
      AtlasFishModel.findByScientificName(scientificName) match {
        case Some(atlasFish) => AtlasFishModel.where(_.scientificName === scientificName).delete; None
        case _ => Some(NOT_EXIST)
      }
  }

  /**
   * Création d'un poisson dans l'Atlas.
   *
   * @param atlasFish Un poisson de l'Atlas.
   * @return
   */
  def create(atlasFish: AtlasFish): (Option[ApiErrorType], AtlasFish) = DB.withTransaction {
    implicit session =>
      AtlasFishModel.findByScientificName(atlasFish.scientificName) match {
        case None => {
          atlasFish.id = Some(insert(atlasFish))
          (None, atlasFish)
        }
        case Some(atlasFishRetrieved) => (Some(ALREADY_EXIST), atlasFishRetrieved)
      }
  }

  /**
   * Modification d'un poisson dans l'Atlas.
   *
   * @param atlasFish Un poisson de l'Atlas.
   * @return Une Option indiquant s'il y a eu erreur ou non.
   */
  def update(atlasFish: AtlasFish): Option[ApiErrorType] = DB.withTransaction {
    implicit session =>
      AtlasFishModel.findById(atlasFish.id.getOrElse(0L)) match {
        case Some(atlasFishRetrieved) => {
          AtlasFishModel.where(_.id === atlasFish.id.get).update(atlasFish)
          None
        }
        case _ => Some(NOT_EXIST)
      }
  }

}
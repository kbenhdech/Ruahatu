package models.atlas.fish

import play.api.db.slick.Config.driver.simple._
import beans.types._
import beans.types.AtlasFishFoodType
import beans.types.AtlasFishDbType
import play.api.db.slick.DB
import play.api.Play.current

/**
 * Définition d'une classe utilitaire pour les tables de types liées aux poissons de l'Atlas.
 *
 * @author Karim BENHDECH
 */
abstract class AtlasFishDbTypeModel[T <: AtlasFishDbType](dbName: String) extends Table[T](dbName) {
  def key = column[String]("KEY", O.PrimaryKey)

  def value = column[String]("VALUE", O.NotNull)

  def indexKey = index(dbName + "_INDEX_KEY", key, unique = true)

  def indexValue = index(dbName + "_INDEX_VALUE", value, unique = true)

  /**
   * Définition des 'Finder'.
   */
  val byKey = createFinderBy(_.key)

  /**
   * Recherche par clé.
   *
   * @param key
   * @return
   */
  def findByKey(key: String): Option[T] = DB.withSession {
    implicit session =>
      this.byKey(key).firstOption
  }

  /**
   * Création d'un nouveau type dans la table 'dbName'.
   *
   * @param atlasFishDbType
   * @return
   */
  def create(atlasFishDbType: T) = {
    DB.withSession {
      implicit session =>
        this.insert(atlasFishDbType)
    }
  }

  /**
   * Suppression à partir de la clé.
   *
   * @param key
   * @return
   */
  def deleteByKey(key: String): Option[ApiErrorType] = DB.withTransaction {
    implicit session =>
      this.findByKey(key) match {
        case Some(atlasFishDbType) => this.where(_.key === key).delete; None
        case _ => Some(NOT_EXIST)
      }
  }

  /**
   * Mise à jour par Clé.
   *
   * @param atlasFishDbType
   * @return
   */
  def update(atlasFishDbType: T): Option[ApiErrorType] = DB.withTransaction {
    implicit session =>
      this.findByKey(atlasFishDbType.key) match {
        case Some(atlasFishDbTypeRetrieved) => {
          this.where(_.key === atlasFishDbType.key).update(atlasFishDbType)
          None
        }
        case _ => Some(NOT_EXIST)
      }
  }

  /**
   * @return L'ensemble des types de la table.
   */
  def getAll(): List[T] = DB.withTransaction {
    implicit session =>
      Query(this).list()
  }

}

/**
 * Table "ATLAS_FISH_WATER_TYPE".
 */
object AtlasFishWaterTypeModel extends AtlasFishDbTypeModel[AtlasFishWaterType]("ATLAS_FISH_WATER_TYPE") {
  def * = key ~ value <>(AtlasFishWaterType.apply _, AtlasFishWaterType.unapply _)
}

/**
 * Table "ATLAS_FISH_FOOD_TYPE".
 */
object AtlasFishFoodTypeModel extends AtlasFishDbTypeModel[AtlasFishFoodType]("ATLAS_FISH_FOOD_TYPE") {
  def * = key ~ value <>(AtlasFishFoodType.apply _, AtlasFishFoodType.unapply _)
}
package beans.types


/**
 * Classe abstraite à implémenter pour chaque type clé/valeur persisté en base.
 *
 * @author Karim BENHDECH
 */
abstract class ApiBbType {
  def key: String;

  def value: String
}

/**
 * Type définit uniquement pour Swagger.
 *
 * @param key
 * @param value
 */
case class AtlasFishDbTypeSwagger(key: String, value: String) extends ApiBbType

object AtlasFishDbTypeSwagger {
  final val allDbType = AtlasFishWaterType.dbType + "," + AtlasFishFoodType.dbType + "," + AtlasFishTemperamentType.dbType + "," + AtlasFishReproductiveType.dbType + "," + AtlasFishSwimmingAreaType.dbType
}

/**
 * Trait à implémenter pour chaque type clé/valeur liés aux poissons de l'Atlas.
 * Persisté en base.
 */
trait AtlasFishDbType extends ApiBbType {
  def key: String;

  def value: String
}

/**
 * Définition des méthodes apply et unapply pour le trait AtlasFishDbType.
 */
object AtlasFishDbType {

  def unapply(atlasFishDbType: AtlasFishDbType) =
    Some(atlasFishDbType.key, atlasFishDbType.value)

  def apply(keyParam: String, valueParam: String) =
    new AtlasFishDbType {
      def key: String = keyParam

      def value: String = valueParam
    }
}

case class AtlasFishWaterType(key: String, value: String) extends AtlasFishDbType

object AtlasFishWaterType {
  final val dbType = "ATLAS_FISH_WATER_TYPE"
}

case class AtlasFishFoodType(key: String, value: String) extends AtlasFishDbType

object AtlasFishFoodType {
  final val dbType = "ATLAS_FISH_FOOD_TYPE"
}

case class AtlasFishTemperamentType(key: String, value: String) extends AtlasFishDbType

object AtlasFishTemperamentType {
  final val dbType = "ATLAS_FISH_TEMPERAMENT_TYPE"
}

case class AtlasFishReproductiveType(key: String, value: String) extends AtlasFishDbType

object AtlasFishReproductiveType {
  final val dbType = "ATLAS_FISH_REPRODUCTIVE_TYPE"
}

case class AtlasFishSwimmingAreaType(key: String, value: String) extends AtlasFishDbType

object AtlasFishSwimmingAreaType {
  final val dbType = "ATLAS_FISH_SWIMMING_AREA_TYPE"
}



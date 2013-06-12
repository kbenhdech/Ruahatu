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

  def apply(key: String, value: String) =
    new AtlasFishDbType {
      def key: String = key

      def value: String = value
    }
}

case class AtlasFishWaterType(key: String, value: String) extends AtlasFishDbType

case class AtlasFishFoodType(key: String, value: String) extends AtlasFishDbType



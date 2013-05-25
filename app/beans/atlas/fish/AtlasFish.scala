package beans.atlas.fish

/**
 * Définition du type AtlasFish, un poisson de l'Atlas.
 *
 * @author Karim BENHDECH
 */
case class AtlasFish(
   id: Option[Long],

   /**
    * Nom scientifique.
    */
   scientificName: String,

   /**
    * Nom commun.
    */
   commonName: String
)

package beans.atlas.fish

/**
 * Définition du type AtlasFish, un poisson de l'Atlas.
 *
 * @author Karim BENHDECH
 */
case class AtlasFish(
   var id: Option[Long],

   /**
    * Nom scientifique.
    */
   var scientificName: String,

   /**
    * Nom commun.
    */
   var commonName: String
)

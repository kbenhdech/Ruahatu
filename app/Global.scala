import beans.atlas.fish.AtlasFish
import beans.atlas.fish.AtlasFish
import beans.types._
import models.atlas.fish._
import beans.atlas.fish.AtlasFish
import play.api._

/**
 *
 * @author Karim BENHDECH
 */
object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}

/**
 * Données a importer lorsque la base de données est vide (En Dev).
 */
object InitialData {

  def insert() {

    // Si aucun poissons dans l'Atlas
    if (AtlasFishModel.count == 0) {

      Seq(
        AtlasFish(Option(1L), "Nom scientifique 1", "Nom commun 1"),
        AtlasFish(Option(2L), "Nom scientifique 2", "Nom commun 2")
      ).foreach(AtlasFishModel.insert)

      Seq(
        AtlasFishFoodType("ALL", "Tous"),
        AtlasFishFoodType("LIVE_FOODS_ONLY", "Nourritures vivantes uniquement"),
        AtlasFishFoodType("VEGETARIAN", "Végétarien (frais, paillettes, congelé, plaquettes, granulés)"), //(fresh, flake, frozen, wafer, pellet)
        AtlasFishFoodType("FLAKE_OR_PELLETS", "Flocons ou granulés"),
        AtlasFishFoodType("LIVE_AND_FROZEN_MEATY", "Viande vivantes et congelés"),
        AtlasFishFoodType("OMNIVOROUS", "Omnivore"),
        AtlasFishFoodType("LIVE_INVERTEBRATES", "Invertébrés vivants"),
        AtlasFishFoodType("CRUSHED_FLAKE_POWDER_OR_LIQUIDS", "Flocons écrasés, en poudres ou liquides"),
        AtlasFishFoodType("OTHER", "Autres (voir description)")
      ).foreach(AtlasFishFoodTypeModel.create)

      Seq(
        AtlasFishWaterType("SALT_WATER", "Eau de mer"),
        AtlasFishWaterType("FRESH_WATER", "Eau douce"),
        AtlasFishWaterType("BRACKISH_WATER", "Eau saumâtre")
      ).foreach(AtlasFishWaterTypeModel.create)

      Seq(
        AtlasFishTemperamentType("ALL", "Tous"),
        AtlasFishTemperamentType("PEACEFUL", "Paisible"),
        AtlasFishTemperamentType("AGGRESSIVE_WITH_ALL_FISH", "Agressif avec tous les poissons"),
        AtlasFishTemperamentType("AGGRESSIVE_WITH_SMALL_FISH_ONLY", "Agressif avec les petits poissons seulement"),
        AtlasFishTemperamentType("AGGRESSIVE_WITH_ITS_OWN_KIND", "Agressif avec sa propre espèce"),
        AtlasFishTemperamentType("FIN_NIPPERS", "Attaque les nageoires") // pinces nageoires
      ).foreach(AtlasFishTemperamentTypeModel.create)

      // http://www.aqua-passion.com/articles/article-65-les-differents-modes-de-reproduction-chez-les-poissons.html
      // http://www.aquariumsite.net/Egg%20Layers.htm
      // http://www.merckmanuals.com/pethealth/exotic_pets/fish/breeding_and_reproduction_of_fish.html
      Seq(
        AtlasFishReproductiveType("ALL", "Tous"),
        AtlasFishReproductiveType("LIVEBEARER", "Ovovivipare"), // Voir alevins // Eau douce
        AtlasFishReproductiveType("EGG_LAYER", "Ovipare"), // (tends eggs) // Eau douce
        AtlasFishReproductiveType("EGG_SCATTERER", "Pondeur sur substrat caché"),
        AtlasFishReproductiveType("EGG_DEPOSITOR", "Pondeur sur substrat découvert"),
        AtlasFishReproductiveType("EGG_BURRIER", "Pondeur en eau libre"),
        AtlasFishReproductiveType("NEST_BUILDER", "Pondeur en nid de bulle"),
        AtlasFishReproductiveType("MOUTH_BROODER", "Incubateur buccal")
      ).foreach(AtlasFishReproductiveTypeModel.create)

      Seq(
        AtlasFishSwimmingAreaType("TOP", "Supérieur"),
        AtlasFishSwimmingAreaType("MIDDLE", "Milieu"),
        AtlasFishSwimmingAreaType("BOTTOM", "Inférieur"),
        AtlasFishSwimmingAreaType("NO_SPECIFIC_AREA", "Aucun domaine spécifique"),
        AtlasFishSwimmingAreaType("MIDDLE_TO_BOTTOM", "Milieu bas")
      ).foreach(AtlasFishSwimmingAreaTypeModel.create)

    }
  }

}
import beans.types.{AtlasFishWaterType, AtlasFishFoodType}
import models.atlas.fish.{AtlasFishWaterTypeModel, AtlasFishFoodTypeModel, AtlasFishModel}
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

    }
  }

}
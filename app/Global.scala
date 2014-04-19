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

      Seq(
        AtlasFishFamilyType("ALL", "All"),
        AtlasFishFamilyType("CHARACIDAE", "characidae"),
        AtlasFishFamilyType("ALESTIIDAE", "alestiidae"),
        AtlasFishFamilyType("ANOSTOMIDAE", "anostomidae"),
        AtlasFishFamilyType("CRENUCHIDAE", "crenuchidae"),
        AtlasFishFamilyType("HEMIODONTIDAE", "hemiodontidae"),
        AtlasFishFamilyType("PROCHILODONTIDAE", "prochilodontidae"),
        AtlasFishFamilyType("CICHLIDAE", "cichlidae"),
        AtlasFishFamilyType("SERRANIDAE", "serranidae"),
        AtlasFishFamilyType("APOGONIDAE", "apogonidae"),
        AtlasFishFamilyType("PLESIOPIDAE", "plesiopidae"),
        AtlasFishFamilyType("OSPHRONEMIDAE", "osphronemidae"),
        AtlasFishFamilyType("COIIDAE", "coiidae"),
        AtlasFishFamilyType("ANABANTIDAE", "anabantidae"),
        AtlasFishFamilyType("ELEOTRIDAE", "eleotridae"),
        AtlasFishFamilyType("GRAMMIDAE", "grammidae"),
        AtlasFishFamilyType("GRAMMISTIDAE", "grammistidae"),
        AtlasFishFamilyType("PSEUDOCHROMIDAE", "pseudochromidae"),
        AtlasFishFamilyType("PRIACANTHIDAE", "priacanthidae"),
        AtlasFishFamilyType("CYPRINIDAE", "cyprinidae"),
        AtlasFishFamilyType("CYPRINIFORMES", "cypriniformes"),
        AtlasFishFamilyType("ACTINOPTERYGII", "actinopterygii"),
        AtlasFishFamilyType("APLOCHEILIDAE", "aplocheilidae"),
        AtlasFishFamilyType("GOODEINAE", "goodeinae"),
        AtlasFishFamilyType("CYPRINODONTIDAE", "cyprinodontidae"),
        AtlasFishFamilyType("POECILIIDAE", "poeciliidae"),
        AtlasFishFamilyType("CALLICHTHYIDAE", "callichthyidae"),
        AtlasFishFamilyType("LORICARIIDAE", "loricariidae"),
        AtlasFishFamilyType("PIMELODIDAE", "pimelodidae"),
        AtlasFishFamilyType("MOCHOKIDAE", "mochokidae"),
        AtlasFishFamilyType("OSTEOGLOSSIDAE", "osteoglossidae"),
        AtlasFishFamilyType("POMACENTRIDAE", "pomacentridae"),
        AtlasFishFamilyType("ANTENNARIIDAE", "antennariidae"),
        AtlasFishFamilyType("OPHICHTHIDAE", "ophichthidae"),
        AtlasFishFamilyType("AULOSTOMIDAE", "aulostomidae"),
        AtlasFishFamilyType("OPHIDIIDAE", "ophidiidae"),
        AtlasFishFamilyType("SCYLIORHINIDAE", "scyliorhinidae"),
        AtlasFishFamilyType("MONOCENTRIDIDAE", "monocentrididae"),
        AtlasFishFamilyType("SCORPAENIDAE", "scorpaenidae"),
        AtlasFishFamilyType("SYNGNATHIDAE", "syngnathidae"),
        AtlasFishFamilyType("MURAENIDAE", "muraenidae"),
        AtlasFishFamilyType("ORECTOLOBIDAE", "orectolobidae"),
        AtlasFishFamilyType("CONGRIDAE", "congridae"),
        AtlasFishFamilyType("HETERODONTIDAE", "heterodontidae"),
        AtlasFishFamilyType("HOLOCENTRIDAE", "holocentridae"),
        AtlasFishFamilyType("CHIMAERIDAE", "chimaeridae"),
        AtlasFishFamilyType("TORPEDINIDAE", "torpedinidae"),
        AtlasFishFamilyType("MACRORHAMPHOSIDAE", "macrorhamphosidae"),
        AtlasFishFamilyType("MORINGUIDAE", "moringuidae"),
        AtlasFishFamilyType("CARCHARHINIDAE", "carcharhinidae"),
        AtlasFishFamilyType("RHINOBATIDAE", "rhinobatidae"),
        AtlasFishFamilyType("SOLENOSTOMIDAE", "solenostomidae"),
        AtlasFishFamilyType("DASYATIDAE", "dasyatidae"),
        AtlasFishFamilyType("TRACHICHTHYIDAE", "trachichthyidae"),
        AtlasFishFamilyType("RAJIDAE", "rajidae"),
        AtlasFishFamilyType("MELANOTAENIIDAE", "melanotaeniidae"),
        AtlasFishFamilyType("MORMYRIDAE", "mormyridae"),
        AtlasFishFamilyType("POLYPTERIDAE", "polypteridae"),
        AtlasFishFamilyType("CHAETODONTIDAE", "chaetodontidae"),
        AtlasFishFamilyType("POMACANTHIDAE", "pomacanthidae"),
        AtlasFishFamilyType("ACANTHURIDAE", "acanthuridae")
      ).foreach(AtlasFishFamilyTypeModel.create)

      Seq(
        AtlasFishBioType("ALL", "All"),
        AtlasFishBioType("AFRICAN_RIVER_RAPIDS", "african river rapids"),
        AtlasFishBioType("LAKE_MALAWI", "lake malawi"),
        AtlasFishBioType("LAKE_TANGANYIKA", "lake tanganyika"),
        AtlasFishBioType("WEST_OR_CENTRAL_AFRICAN_RIVER", "west or central african river"),
        AtlasFishBioType("SOUTHEAST_ASIAN_FAST_MOVING_STREAM", "southeast asian fast-moving stream"),
        AtlasFishBioType("SOUTHERN_AFRICAN_SWAMP", "southern african swamp"),
        AtlasFishBioType("SOUTHEAST_ASIAN_RIVER", "southeast asian river"),
        AtlasFishBioType("SOUTHEAST_ASIAN_BLACKWATER_POOL", "southeast asian blackwater pool"),
        AtlasFishBioType("SOUTHEAST_ASIAN_ESTUARY", "southeast asian estuary"),
        AtlasFishBioType("NORTH_AMERICAN_ESTUARY", "north american estuary"),
        AtlasFishBioType("NORTH_AMERICAN_LAKE", "north american lake"),
        AtlasFishBioType("NORTH_AMERICAN_RIVER", "north american river"),
        AtlasFishBioType("NORTH_AMERICAN_SWAMP", "north american swamp"),
        AtlasFishBioType("NORTHERN_AUSTRALIA_RAINFOREST_CREEK", "northern australia rainforest creek"),
        AtlasFishBioType("CENTRAL_AMERICAN_FAST_MOVING_STREAM", "central american fast-moving stream"),
        AtlasFishBioType("CENTRAL_AMERICAN_ESTUARY", "central american estuary"),
        AtlasFishBioType("CENTRAL_AMERICAN_RIVER", "central american river"),
        AtlasFishBioType("SOUTH_AMERICAN_WHITEWATER_RIVER", "south american whitewater river"),
        AtlasFishBioType("SOUTH_AMERICAN_CLEARWATER_STREAM", "south american clearwater stream"),
        AtlasFishBioType("SOUTH_AMERICAN_BLACKWATER_CREEK", "south american blackwater creek"),
        AtlasFishBioType("SOUTH_AMERICAN_BLACKWATER_RIVER", "south american blackwater river"),
        AtlasFishBioType("OTHER", "other, see description."),
        AtlasFishBioType("LAKE_VICTORIA", "lake victoria")
      ).foreach(AtlasFishBioTypeModel.create)

    }
  }

}
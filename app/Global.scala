import models.atlas.fish.AtlasFishModel
import beans.atlas.fish.AtlasFish
import play.api._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}

/**
 * Données a importer lorsque la base de données est vide(En Dev).
 */
object InitialData {

  def insert() {
    if (AtlasFishModel.count == 0) {
      Seq(
        AtlasFish(Option(1L), "Nom scientifique 1", "Nom commun 1"),
        AtlasFish(Option(2L), "Nom scientifique 2", "Nom commun 2")
      ).foreach(AtlasFishModel.insert)
    }
  }

}
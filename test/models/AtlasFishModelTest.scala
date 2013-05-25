package models

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import models.atlas.fish.AtlasFishModel

class AtlasFishModelTest extends Specification {

  import models._
  import beans._

  "AtlasFish" should {

    "être retrouvé par ID" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(atlasFish) = AtlasFishModel.findById(1)

        atlasFish.scientificName must equalTo("Nom scientifique 1")
        atlasFish.commonName must equalTo("Nom commun 1")
      }
    }
  }

  "être retrouvé par nom scientifique" in {
    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
      val Some(atlasFish) = AtlasFishModel.findByScientificName("Nom scientifique 1")

      atlasFish.id must equalTo(Some(1L))
      atlasFish.commonName must equalTo("Nom commun 1")
    }
  }

}
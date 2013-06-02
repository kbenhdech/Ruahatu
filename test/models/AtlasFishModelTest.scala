package models

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import models.atlas.fish.AtlasFishModel
import beans.atlas.fish.AtlasFish
import beans.types.ALREADY_EXIST

class AtlasFishModelTest extends Specification {

  "AtlasFish findById" should {

    "être retrouvé par ID si existant" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(atlasFish) = AtlasFishModel.findById(1)

        atlasFish.scientificName must equalTo("Nom scientifique 1")
        atlasFish.commonName must equalTo("Nom commun 1")
      }
    }

    "retourner None en cas d'ID inexistant" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val atlasFishOption = AtlasFishModel.findById(1000)

        atlasFishOption must equalTo(None)
      }
    }
  }

  "AtlasFish findByScientificName" should {

    "être retrouvé par nom scientifique si existant" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(atlasFish) = AtlasFishModel.findByScientificName("Nom scientifique 1")

        atlasFish.id must equalTo(Some(1L))
        atlasFish.commonName must equalTo("Nom commun 1")
      }
    }

    "retourner None en cas de nom scientifique inexistant" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val atlasFishOption = AtlasFishModel.findByScientificName("Nom scientifique inexistant")

        atlasFishOption must equalTo(None)
      }
    }
  }

  "AtlasFish count" should {

    "retourner le bon nombre de poissons dans l'Atlas" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val numberOfAtlasFish = AtlasFishModel count

        numberOfAtlasFish must equalTo(2)
      }
    }

  }

  "AtlasFish create" should {

    "creer un poissons dans l'Atlas si le nom scientifique est inexistant et le retroouver par la suite" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val atlasFish = new AtlasFish(None, "nouveau nom scientifique", "nouveau nom commun")
        val atlasFishErrorTypeOption = AtlasFishModel.create(atlasFish)._1

        atlasFishErrorTypeOption must equalTo(None)

        val Some(atlasFishRetrieved) = AtlasFishModel.findByScientificName(atlasFish.scientificName)

        atlasFishRetrieved.commonName must equalTo(atlasFish.commonName)
      }
    }

    "renvoyer une erreur si un poisson portant le nom scientifique existe déjà" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val atlasFish = new AtlasFish(None, "Nom scientifique 1", "Nom commun 1")
        val atlasFishErrorTypeOption = AtlasFishModel.create(atlasFish)

        atlasFishErrorTypeOption._1 must equalTo(Some(ALREADY_EXIST))
        atlasFishErrorTypeOption._1.get must equalTo(ALREADY_EXIST)
      }
    }

  }

}
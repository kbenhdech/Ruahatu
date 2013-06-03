package models

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import models.atlas.fish.AtlasFishModel
import beans.atlas.fish.AtlasFish
import beans.types.{NOT_EXIST, ALREADY_EXIST}

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

    "creer un poisson dans l'Atlas si le nom scientifique est inexistant et le retroouver par la suite" in {
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

  "AtlasFish deleteByScientificName" should {

    "supprimer un poisson avec un nom scientifique inexistant dans l'Atlas" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val atlasFishErrorTypeOption = AtlasFishModel.deleteByScientificName("nom scientifique inexistant")

        atlasFishErrorTypeOption must equalTo(Some(NOT_EXIST))
        atlasFishErrorTypeOption.get must equalTo(NOT_EXIST)
      }
    }

    "supprimer un poisson avec un nom scientifique existant dans l'Atlas" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val numberAtlasFishBeforeDelete = AtlasFishModel.count
        val atlasFishErrorTypeOption = AtlasFishModel.deleteByScientificName("Nom scientifique 1")

        atlasFishErrorTypeOption must equalTo(None)

        val numberAtlasFishAfterDelete = AtlasFishModel.count
        numberAtlasFishAfterDelete must equalTo(numberAtlasFishBeforeDelete-1)
      }
    }

  }

  "AtlasFish update" should {

    "modification d'un poisson avec un ID inexistant dans l'Atlas" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val atlasFish = new AtlasFish(Some(1000), "Nom scientifique 1000", "Nom commun 1000")
        val atlasFishErrorTypeOption = AtlasFishModel.update(atlasFish)

        atlasFishErrorTypeOption must equalTo(Some(NOT_EXIST))
        atlasFishErrorTypeOption.get must equalTo(NOT_EXIST)
      }
    }

    "modification d'un poisson avec un ID existant dans l'Atlas" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(atlasFishBeforeUpdate) = AtlasFishModel.findByScientificName("Nom scientifique 1")
        atlasFishBeforeUpdate.commonName = "Nouveau nom commun"
        val atlasFishErrorTypeOption = AtlasFishModel.update(atlasFishBeforeUpdate)

        atlasFishErrorTypeOption must equalTo(None)

        val Some(atlasFishAfterUpdate) = AtlasFishModel.findByScientificName("Nom scientifique 1")
        atlasFishAfterUpdate.commonName must equalTo("Nouveau nom commun")
      }
    }

  }

}
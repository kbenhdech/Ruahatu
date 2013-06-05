package controllers

import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.{FakeRequest, FakeApplication}
import play.api.test.Helpers._
import beans.atlas.fish.AtlasFish

class AtlasFishControllerTest extends Specification {

  implicit val atlasFishReads = Json.reads[AtlasFish]
  implicit val atlasFishWrites = Json.writes[AtlasFish]

  /*
  "AtlasFish API getAtlasFishById" should {

    "renvoyer 200 et le bon content-type" in {
      running(FakeApplication()) {
        val response = route(FakeRequest(GET, "/atlas/fish/1")).get
        status(response) must equalTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
      }
    }

    "avoir des informations correctes" in {
      running(FakeApplication()) {
        val response = route(FakeRequest(GET, "/atlas/fish/1")).get
        val atlasFish = Json.fromJson[AtlasFish](Json.parse(contentAsString(response))).fold(
          valid   = { t => t},
          invalid = { e => {
            null
          }}
        )
        atlasFish.scientificName must contain("Nom scientifique 1")
      }
    }
  }
  */

  "AtlasFish API getAtlasFishByScientificName" should {

    "renvoyer 200 et le bon content-type" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val response = route(FakeRequest(GET, "/atlas/fish/Nom%20scientifique%201")).get
        status(response) must equalTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
      }
    }

    "avoir des informations correctes" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val response = route(FakeRequest(GET, "/atlas/fish/Nom%20scientifique%202")).get
        val atlasFish = Json.fromJson[AtlasFish](Json.parse(contentAsString(response))).fold(
          valid = {
            t => t
          },
          invalid = {
            e => {
              null
            }
          }
        )
        atlasFish.commonName must contain("Nom commun 2")
        atlasFish.id mustEqual (Some(2))
      }
    }
  }

  "AtlasFish API deleteAtlasFishByScientificName" should {

    "renvoyer 404 si le nom scientifique est inexistant dans l'Atlas" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val response = route(FakeRequest(DELETE, "/atlas/fish/Nom%20scientifique%20Inexistant")).get
        status(response) must equalTo(NOT_FOUND)
      }
    }

    "renvoyer 204 si la suppression est un succés" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val response = route(FakeRequest(DELETE, "/atlas/fish/Nom%20scientifique%201")).get
        status(response) must equalTo(NO_CONTENT)
      }
    }
  }

  "AtlasFish API createAtlasFish" should {

    "renvoyer 400 si le poisson existe dans l'Atlas (nom scientifique)" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val body: String = "{\"scientificName\":\"Nom scientifique 1\", \"commonName\": \"commonName\"}";
        val response = route(FakeRequest(POST, "/atlas/fish").withJsonBody(Json.parse(body))).get
        status(response) must equalTo(BAD_REQUEST)
      }
    }

    "renvoyer 200 si la création est un succés" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val body: String = "{\"scientificName\":\"Nom scientifique 1000\", \"commonName\": \"commonName\"}";
        val response = route(FakeRequest(POST, "/atlas/fish").withJsonBody(Json.parse(body))).get
        status(response) must equalTo(OK)
      }
    }
  }

  "AtlasFish API updateAtlasFish" should {

    "renvoyer 400 si le poisson n'existe pas dans l'Atlas (nom scientifique)" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val body: String = "{\"id\":\"1000\",\"scientificName\":\"Nom scientifique 1000\", \"commonName\": \"commonName\"}";
        val response = route(FakeRequest(PUT, "/atlas/fish").withJsonBody(Json.parse(body))).get
        status(response) must equalTo(BAD_REQUEST)
      }
    }

    "renvoyer 200 si la modification est un succés" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val body: String = "{\"id\":\"1\",\"scientificName\":\"Nom scientifique 1\", \"commonName\": \"commonName\"}";
        val response = route(FakeRequest(PUT, "/atlas/fish").withJsonBody(Json.parse(body))).get
        status(response) must equalTo(OK)
      }
    }
  }

}
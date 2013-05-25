package controllers

import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.{FakeRequest, FakeApplication}
import play.api.test.Helpers._
import beans.atlas.fish.AtlasFish

class AtlasFishControllerTest extends Specification  {

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
      running(FakeApplication()) {
        val response = route(FakeRequest(GET, "/atlas/fish/Nom%20scientifique%201")).get
        status(response) must equalTo(OK)
        contentType(response) must beSome.which(_ == "application/json")
      }
    }

    "avoir des informations correctes" in {
      running(FakeApplication()) {
        val response = route(FakeRequest(GET, "/atlas/fish/Nom%20scientifique%202")).get
        val atlasFish = Json.fromJson[AtlasFish](Json.parse(contentAsString(response))).fold(
          valid   = { t => t},
          invalid = { e => {
            null
          }}
        )
        atlasFish.commonName must contain("Nom commun 2")
        atlasFish.id mustEqual (Some(2))
      }
    }
  }

}
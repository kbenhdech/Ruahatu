package controllers

import play.api.mvc._
import javax.ws.rs.PathParam
import com.wordnik.swagger.annotations._
import play.api.libs.json.Json
import models.atlas.fish.AtlasFishModel
import beans.atlas.fish.AtlasFish

/**
 *
 */
@Api(value = "/atlas/fish", listingPath = "/api-docs.{format}/atlas/fish", description = "APIs de gestion de poissons de l'Atlas")
object AtlasFishController extends Controller {

  implicit val atlasFishReads = Json.reads[AtlasFish]
  implicit val atlasFishWrites = Json.writes[AtlasFish]

  /*
  @ApiOperation(value = "Recherche un poisson de l'Atlas par ID", notes = "Retourne un poisson de l'Atlas",responseClass = "AtlasFish", httpMethod = "GET")
  @ApiErrors(Array(new ApiError(code = 404, reason = "Poisson d'Atlas non trouvé")))
  def getAtlasFishById(@ApiParam(value = "ID du poisson de l'Atlas")@PathParam("id") id: Long) = Action { implicit request =>
    AtlasFishModel.findById(id) match {
      case Some(atlasFish) => Ok(Json.toJson(atlasFish)).as(JSON)
      case _ => NotFound
    }
  }
  */

  @ApiOperation(value = "Recherche un poisson de l'Atlas par son nom scientifique", notes = "Retourne un poisson de l'Atlas",responseClass = "AtlasFish", httpMethod = "GET")
  @ApiErrors(Array(new ApiError(code = 404, reason = "Poisson de l'Atlas non trouvé")))
  def getAtlasFishByScientificName(@ApiParam(value = "Nom Scientifique du poisson de l'Atlas")@PathParam("scientificName") scientificName: String) = Action { implicit request =>
    AtlasFishModel.findByScientificName(scientificName) match {
      case Some(atlasFish) => Ok(Json.toJson(atlasFish)).as(JSON)
      case _ => NotFound
    }
  }

}
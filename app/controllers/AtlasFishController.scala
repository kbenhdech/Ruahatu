package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import javax.ws.rs.PathParam
import com.wordnik.swagger.annotations._
import models.atlas.fish.AtlasFishModel
import beans.atlas.fish.AtlasFish
import beans.types.AtlasFishErrorType
import play.api.libs.json._

/**
 *
 */
@Api(value = "/atlas/fish", listingPath = "/api-docs.{format}/atlas/fish", description = "APIs de gestion de poissons de l'Atlas")
object AtlasFishController extends Controller {

  implicit val atlasFishReads = Json.reads[AtlasFish]
  implicit val atlasFishWrites = Json.writes[AtlasFish]

  implicit val atlasFishErrorTypeFormat = Json.format[AtlasFishErrorType]

  /**
   * Description du formulaire d'un AtlasFIsh.
   */
  val atlasFishForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "scientificName" -> nonEmptyText,
      "commonName" -> nonEmptyText
    )(AtlasFish.apply)(AtlasFish.unapply)
  )

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

  @ApiOperation(value = "Recherche un poisson de l'Atlas par son nom scientifique", notes = "Retourne un poisson de l'Atlas", responseClass = "beans.atlas.fish.AtlasFish", httpMethod = "GET")
  @ApiErrors(Array(new ApiError(code = 404, reason = "Poisson de l'Atlas non trouvé")))
  def getAtlasFishByScientificName(@ApiParam(value = "Nom Scientifique du poisson de l'Atlas") @PathParam("scientificName") scientificName: String) = Action {
    implicit request =>
      AtlasFishModel.findByScientificName(scientificName) match {
        case Some(atlasFish) => Ok(Json.toJson(atlasFish)).as(JSON)
        case _ => NotFound
      }
  }

  @ApiOperation(value = "Supprime un poisson de l'Atlas par son nom scientifique", notes = "Ne retourne rien mise à pars le status", httpMethod = "DELETE")
  @ApiErrors(Array(new ApiError(code = 404, reason = "Poisson de l'Atlas non trouvé")))
  def deleteAtlasFishByScientificName(@ApiParam(value = "Nom Scientifique du poisson de l'Atlas") @PathParam("scientificName") scientificName: String) = Action {
    implicit request =>
      AtlasFishModel.deleteByScientificName(scientificName) match {
        case Some(atlasFishErrorType) => NotFound
        case None => NoContent
      }
  }

  @ApiOperation(value = "Crée un poisson de l'Atlas", notes = "Retourne un poisson de l'Atlas", responseClass = "beans.atlas.fish.AtlasFish", httpMethod = "POST")
  @ApiErrors(Array(new ApiError(code = 400, reason = "[ALREADY_EXIST] Poisson déjà existant dans l'Atlas")))
  @ApiParamsImplicit(Array(
    new ApiParamImplicit(name = "atlasFish", value = "an atlashFish", required = true, dataType = "beans.atlas.fish.AtlasFish", paramType = "body")))
  def createAtlasFish() = Action {
    implicit request =>
      atlasFishForm.bindFromRequest.fold(
        formWithErrors => BadRequest,
        atlasFishFromForm => {
          AtlasFishModel.create(atlasFishFromForm) match {
            case (None, atlasFish) => Ok(Json.toJson(atlasFish)).as(JSON)
            case (Some(error), atlasFish) => BadRequest(Json.toJson(error))
          }
        }
      )
  }

  @ApiOperation(value = "Modifie un poisson existant dans l'Atlas", notes = "Retourne un poisson de l'Atlas", responseClass = "beans.atlas.fish.AtlasFish", httpMethod = "PUT")
  @ApiErrors(Array(new ApiError(code = 400, reason = "[NOT_EXIST] Poisson non trouvé dans l'Atlas")))
  @ApiParamsImplicit(Array(
    new ApiParamImplicit(name = "atlasFish", value = "an atlashFish", required = true, dataType = "beans.atlas.fish.AtlasFish", paramType = "body")))
  def updateAtlasFish() = Action {
    implicit request =>
      atlasFishForm.bindFromRequest.fold(
        formWithErrors => BadRequest,
        atlasFishFromForm => {
          AtlasFishModel.update(atlasFishFromForm) match {
            case Some(atlasFishErrorType) => BadRequest(Json.toJson(atlasFishErrorType))
            case None => Ok(Json.toJson(atlasFishFromForm)).as(JSON)
          }
        }
      )

  }

}
package controllers

import play.mvc._
import javax.ws.rs.PathParam
import com.wordnik.swagger.annotations._
import models.atlas.fish._
import beans.types._
import play.api.libs.json._
import beans.types.AtlasFishFoodType
import beans.types.AtlasFishWaterType
import scala.Some
import play.data.Form
import play.api.data.Form

/**
 * Controller des APIs relatives à la gestion des poissons de l'Atlas.
 *
 * @author Karim BENHDECH
 */
@Api(value = "/atlas/fish/type", listingPath = "/api-docs.{format}/atlas/fish/type",
  description = "APIs de gestion d'informations de type clé/valeur liées aux poissons de l'Atlas (exemple: régime alimentaire, eau de vie...).")
object AtlasFishDbTypeController extends Controller {

  implicit val atlasFishWaterTypeReads = Json.reads[AtlasFishWaterType]
  implicit val atlasFishWaterTypeWrites = Json.writes[AtlasFishWaterType]

  implicit val atlasFishFoodTypeReads = Json.reads[AtlasFishFoodType]
  implicit val atlasFishFoodTypeWrites = Json.writes[AtlasFishFoodType]

  implicit val atlasFishDbTypeWrites = Json.writes[AtlasFishDbType]
  implicit val atlasFishDbTypeReads = Json.reads[AtlasFishDbType]

  //implicit val apiBbTypeWrites = Json.writes[ApiBbType]
  //implicit val apiBbTypeReads = Json.reads[ApiBbType]

  implicit val apiErrorTypeFormat = Json.format[ApiErrorType]

  /**
   * Description du formulaire d'un AtlasFIsh.
   */
  val atlasFishDbTypeForm = Form(
    mapping(
      "key" -> nonEmptyText,
      "value" -> nonEmptyText
    )(AtlasFishDbType.apply)(AtlasFishDbType.unapply)
  )

  /**
   * Appel une méthode en lui passant en paramétre le type/table des informations demandées.
   *
   * @param dbType
   * @param treatment Méthode de traitement spécifique.
   * @return
   */
  private def callSpecificTreatment(dbType: String, treatment: AtlasFishDbTypeModel[AtlasFishDbType] => Result): Result = {
    dbType match {
      case AtlasFishWaterType.dbType => treatment(AtlasFishWaterTypeModel.asInstanceOf[AtlasFishDbTypeModel[AtlasFishDbType]])
      case AtlasFishFoodType.dbType => treatment(AtlasFishFoodTypeModel.asInstanceOf[AtlasFishDbTypeModel[AtlasFishDbType]])
      case AtlasFishTemperamentType.dbType => treatment(AtlasFishTemperamentTypeModel.asInstanceOf[AtlasFishDbTypeModel[AtlasFishDbType]])
      case AtlasFishReproductiveType.dbType => treatment(AtlasFishReproductiveTypeModel.asInstanceOf[AtlasFishDbTypeModel[AtlasFishDbType]])
      case AtlasFishSwimmingAreaType.dbType => treatment(AtlasFishSwimmingAreaTypeModel.asInstanceOf[AtlasFishDbTypeModel[AtlasFishDbType]])
      case AtlasFishFamilyType.dbType => treatment(AtlasFishFamilyTypeModel.asInstanceOf[AtlasFishDbTypeModel[AtlasFishDbType]])
      case AtlasFishBioType.dbType => treatment(AtlasFishBioTypeModel.asInstanceOf[AtlasFishDbTypeModel[AtlasFishDbType]])
      case _ => NotFound
    }
  }

  @ApiOperation(value = "Recherche de données liés à un poisson de l'Atlas", notes = "Retourne les informations typées", responseClass = "beans.types.AtlasFishDbTypeSwagger", httpMethod = "GET")
  @ApiErrors(Array(
    new ApiError(code = 404, reason = "Ce type n'existe pas"),
    new ApiError(code = 400, reason = "[NOT_EXIST] Clé inexistante")
  ))
  def getAtlasFishDbTypeByDbTypeAndLabel(
                                          @ApiParam(value = "Type de la donnée", allowableValues = AtlasFishDbTypeSwagger.allDbType, required = true) @PathParam("dbType") dbType: String,
                                          @ApiParam(value = "Clé de la donnée", required = true) @PathParam("key") key: String) = Action {
    implicit request =>
      def treatment(model: AtlasFishDbTypeModel[AtlasFishDbType]): Result = {
        model.findByKey(key) match {
          case Some(atlasFish) => Ok(Json.toJson(atlasFish)).as(JSON)
          case _ => BadRequest(NOT_EXIST)
        }
      }
      callSpecificTreatment(dbType, treatment)
  }

  @ApiOperation(value = "Crée une nouvelle clé pour le type donné", notes = "Retourne les inforamtions entrée en cas de succés", responseClass = "beans.atlas.fish.AtlasFishDbTypeSwagger", httpMethod = "POST")
  @ApiErrors(Array(new ApiError(code = 400, reason = "[ALREADY_EXIST] La clé existe déjà")))
  @ApiParamsImplicit(Array(
    new ApiParamImplicit(name = "dbType", allowableValues = AtlasFishDbTypeSwagger.allDbType, required = true, dataType = "String", paramType = "path"),
    new ApiParamImplicit(name = "atlasFishDbTypeSwagger", value = "Une instance d'un type de donné", required = true, dataType = "beans.atlas.fish.AtlasFishDbTypeSwagger", paramType = "body")))
  def createAtlasFishDbType() = Action {
    implicit request =>
      atlasFishDbTypeForm.bindFromRequest.fold(
        formWithErrors => BadRequest,
        atlasFishFromForm => {
          AtlasFishModel.create(atlasFishFromForm) match {
            case (None, atlasFish) => Ok(Json.toJson(atlasFish)).as(JSON)
            case (Some(error), atlasFish) => BadRequest(Json.toJson(error))
          }
        }
      )
  }

}
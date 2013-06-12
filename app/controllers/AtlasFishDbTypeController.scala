package controllers

import play.api.mvc._
import javax.ws.rs.PathParam
import com.wordnik.swagger.annotations._
import models.atlas.fish.{AtlasFishWaterTypeModel, AtlasFishFoodTypeModel, AtlasFishDbTypeModel}
import beans.types._
import play.api.libs.json._
import beans.types.AtlasFishFoodType
import scala.Some
import beans.types.AtlasFishWaterType

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
  /*
  val atlasFishDbTypeForm = Form(
    mapping(
      "scientificName" -> nonEmptyText,
      "commonName" -> nonEmptyText
    )(AtlasFishDbType.apply)(AtlasFishDbType.unapply)
  )
  */

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
      case _ => NotFound
    }
  }

  @ApiOperation(value = "Recherche de données liés à un poisson de l'Atlas", notes = "Retourne les informations typées", responseClass = "beans.types.AtlasFishDbTypeSwagger", httpMethod = "GET")
  @ApiErrors(Array(new ApiError(code = 404, reason = "Information on trouvée")))
  def getAtlasFishDbTypeByDbTypeAndLabel(
                                          @ApiParam(value = "Type de la donnée", allowableValues = "ATLAS_FISH_WATER_TYPE,ATLAS_FISH_FOOD_TYPE", required = true) @PathParam("dbType") dbType: String,
                                          @ApiParam(value = "Clé de la donnée", required = true) @PathParam("key") key: String) = Action {
    implicit request =>
      def treatment(model: AtlasFishDbTypeModel[AtlasFishDbType]): Result = {
        model.findByKey(key) match {
          case Some(atlasFish) => Ok(Json.toJson(atlasFish)).as(JSON)
          case _ => NotFound
        }
      }
      callSpecificTreatment(dbType, treatment)
  }

}
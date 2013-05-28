package beans.types

import play.api.libs.json._

sealed case class AtlasFishErrorType(typeText: String, typeDescription: String)

object AtlasFishErrorType extends Function2[String, String, AtlasFishErrorType] {
  implicit val personReads = Json.reads[AtlasFishErrorType]
}

object ALREADY_EXIST extends AtlasFishErrorType("ALREADY_EXIST", "Un objet portant ce nom existe déjà")

object IMPOSSIBLE_TO_DELETE extends AtlasFishErrorType("IMPOSSIBLE_TO_DELETE", "Il n'est pas possible de supprimer cet objet")

object NOT_EXIST extends AtlasFishErrorType("NOT_EXIST", "L'objet n'existe pas")
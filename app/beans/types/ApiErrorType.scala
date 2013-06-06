package beans.types

import play.api.libs.json._

/**
 *
 *
 * @param typeText Type de l'erreur, en majuscule. Pour un programme.
 * @param typeDescription  Description pour que l'erreur soit compréhensible par un humain.
 */
sealed case class ApiErrorType(typeText: String, typeDescription: String)

object ApiErrorType extends ((String, String) => ApiErrorType) {
  implicit val apiErrorTypeReads = Json.reads[ApiErrorType]
}

object ALREADY_EXIST extends ApiErrorType("ALREADY_EXIST", "Un objet portant ce nom existe déjà")

object IMPOSSIBLE_TO_DELETE extends ApiErrorType("IMPOSSIBLE_TO_DELETE", "Il n'est pas possible de supprimer cet objet")

object NOT_EXIST extends ApiErrorType("NOT_EXIST", "L'objet n'existe pas")
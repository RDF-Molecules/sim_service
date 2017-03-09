package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import services.similarity.GADES

/**
 * This controller demonstrates how to use dependency injection to
 * bind a component into a controller class. The class creates an
 */
@Singleton
class SimilarityController @Inject() extends Controller {

  /**
    * Create an action that responds with the similarity value
    * between two entities in the graph
   */
  def similarity (method: String, minimal: Option[String]) = Action { request =>
    val json = request.body.asJson
    json match {
      case Some(value) =>
        val tasks = (value \ "tasks").as[List[SimilaryTask]]

        minimal match {
          case Some(v) =>
            val result = tasks map { r => new SimilaryTask(None, None, Some(GADES.similarity(r.uri1.get, r.uri2.get, method))) }
            Ok(Json.toJson(result))
          case None =>
            val result = tasks map { r => new SimilaryTask(r.uri1, r.uri2, Some(GADES.similarity(r.uri1.get, r.uri2.get, method))) }
            Ok(Json.toJson(result))
        }
      case None => BadRequest("No Json Sent!!!")
    }
  }

  implicit val similarTaskReader = Json.reads[SimilaryTask]
  implicit val similarTaskWrites = Json.writes[SimilaryTask]

}

case class SimilaryTask (uri1: Option[String], uri2: Option[String], value: Option[Double])
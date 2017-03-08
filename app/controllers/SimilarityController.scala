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
  def similarity (method: String) = Action { request =>
    val json = request.body.asJson
    json match {
      case Some(value) =>
        val tasks = (value \ "tasks").as[List[SimilaryTask]]
        val result = tasks map { r => new SimilaryTask(r.uri1, r.uri2, Some(GADES.similarity(r.uri1, r.uri2, method))) }
        Ok(Json.toJson(result))
      case None => BadRequest("No Json Sent!!!")
    }
  }

  implicit val similarTaskReader = Json.reads[SimilaryTask]
  implicit val similarTaskWrites = Json.writes[SimilaryTask]

}

case class SimilaryTask (uri1: String, uri2: String, value: Option[Double])
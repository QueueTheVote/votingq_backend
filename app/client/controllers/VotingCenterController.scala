package client.controllers

import client.models.{CentersRequest, MinimalActiveVotingCenter}
import javax.inject._
import play.api.mvc._
import service.CentersService
import client.models.WriteableImplicits._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class VotingCenterController @Inject()(
  val controllerComponents: ControllerComponents,
  val centerService: CentersService
) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
/*
  def getCenter(centerId: Long): Action[AnyContent] = Action {
    centerService.getCenter(centerId)
  }
*/
  def getCenters(
    electionId: Long,
    street1: String,
    street2: Option[String],
    city: String,
    state: String,
    zip: Option[String]
  ): Action[AnyContent] = Action {
    val request = CentersRequest(electionId, street1, street2, city, state, zip)
    val centers = centerService.getCenters(request)
    val response = centers.flatMap(MinimalActiveVotingCenter.fromElectionVotingCenter)
    Ok(response.toList)
  }
}


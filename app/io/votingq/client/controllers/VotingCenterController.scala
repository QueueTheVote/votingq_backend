package io.votingq.client.controllers
import io.votingq.client.WriteableImplicits._
import io.votingq.client.models.{CentersRequest, CurrentUserVotingQueue, DetailedActiveVotingCenter, MinimalActiveVotingCenter}
import io.votingq.service.{CentersService, QueueService}
import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class VotingCenterController @Inject()(
  val controllerComponents: ControllerComponents,
  val centerService: CentersService,
  val queueService: QueueService
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

  def getCenter(
    centerId: Long,
    voterId: Option[Long] = None
  ): Action[AnyContent] = Action {
    val maybeResponse = centerService.getCenter(centerId)
      .flatMap{center =>
        DetailedActiveVotingCenter.fromElectionVotingCenter(center, voterId)
      }
    maybeResponse.map(r => Ok(r)).getOrElse(NotFound)
  }

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

  def joinQueue(
    queueId: Long,
    voterIds: Set[Long],
    userVoterId: Long
  ): Action[AnyContent] = Action {
    if (voterIds.isEmpty) {
      BadRequest("Non-empty voter ID set required.")
    } else if (!voterIds.contains(userVoterId)){
      BadRequest("Set of voterIds must contain current user's voterId.")
    } else {
      queueService.joinQueue(queueId, voterIds)
        .fold(failure => UnprocessableEntity(failure.msg),
          queueSuccess => Ok(CurrentUserVotingQueue.fromVoterId(queueSuccess, userVoterId)))
    }
  }
}


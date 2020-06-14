package client.models

import cats.data.NonEmptyList
import play.api.libs.json.{Json, Writes}
import service.models.{Address, ElectionVotingCenter, PollingHours, VotingQueue}

trait BareVotingCenter {

  def id: Long

  def name: String

  def address: Address
}

case class MinimalActiveVotingCenter(
  override val id: Long,
  override val name: String,
  override val address: Address,
  pollingHours: PollingHours,
  currentQueue: VotingQueue
) extends BareVotingCenter

object MinimalActiveVotingCenter {

  def fromElectionVotingCenter(
    center: ElectionVotingCenter
  ): Option[MinimalActiveVotingCenter] = {
    (NonEmptyList.fromList(center.pollingHours.toList), center.currentQueue) match {
      case (Some(hours), Some(queue)) => Some(MinimalActiveVotingCenter(
        id = center.id,
        name = center.name,
        address = center.address,
        pollingHours = hours.head,
        currentQueue = queue
      ))
      case _ => None
    }
  }

  implicit val writes: Writes[MinimalActiveVotingCenter] = Json.writes[MinimalActiveVotingCenter]
}

case class DetailedActiveVotingCenter(
  override val id: Long,
  override val name: String,
  override val address: Address,
) extends BareVotingCenter

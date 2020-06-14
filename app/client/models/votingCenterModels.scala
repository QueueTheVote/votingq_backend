package client.models

import cats.data.NonEmptyList
import play.api.libs.json.{Json, Writes}
import service.models.{Address, ElectionVotingCenter, PollingHours, VotingQueue}

trait ClientVotingCenter {

  def id: Long

  def name: String

  def address: Address
}

case class MinimalActiveVotingCenter(
  override val id: Long,
  override val name: String,
  override val address: Address,
  pollingHours: PollingHours,
  currentQueue: MinimalVotingQueue
) extends ClientVotingCenter

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
        currentQueue = MinimalVotingQueue(queue)
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
  currentQueue: ClientVotingQueue,
  voterRequirements: Vector[String] = Vector(
    "Driver's License, State ID number, or Social Security Number",
    "Something Else"
  )
) extends ClientVotingCenter

object DetailedActiveVotingCenter {
  def fromElectionVotingCenter(center: ElectionVotingCenter, voterId: Long): Option[DetailedActiveVotingCenter] = {
    center.currentQueue.map{currentQueue =>
      val queue = CurrentUserVotingQueue.fromVoterId(currentQueue, voterId).getOrElse(MinimalVotingQueue(currentQueue))
      DetailedActiveVotingCenter(
        id = center.id,
        name = center.name,
        address = center.address,
        currentQueue = queue
      )
    }
  }
  implicit val writes: Writes[DetailedActiveVotingCenter] = Json.writes[DetailedActiveVotingCenter]
}

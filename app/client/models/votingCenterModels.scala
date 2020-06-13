package client.models

import play.api.libs.json.{Json, Writes}
import service.models.{Address, ElectionVotingCenter, PollingHours, VotingQueue}

trait VotingCenter {
  def id: Long
  def name: String
  def address: Address
}

case class VotingCenterWithHours(
  override val id: Long,
  override val name: String,
  override val address: Address,
  pollingHours: Vector[PollingHours],
) extends VotingCenter

object VotingCenterWithHours {

  def fromElectionVotingCenter(
    center: ElectionVotingCenter
  ): VotingCenterWithHours = VotingCenterWithHours(
    id = center.id,
    name = center.name,
    address = center.address,
    pollingHours = center.pollingHours
  )

  implicit val writes: Writes[VotingCenterWithHours] = Json.writes[VotingCenterWithHours]
}

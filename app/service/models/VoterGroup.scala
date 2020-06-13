package service.models

import play.api.libs.json.{Json, Writes}

/**
 * @param position Position in Queue
 */
case class VoterGroup(
  election: Election,
  center: ElectionVotingCenter,
  position: Int,
  voters: Vector[Voter]
)
object VoterGroup {

  implicit val writes: Writes[VoterGroup] = Json.writes[VoterGroup]
}

package service.models

import java.time.ZonedDateTime

import play.api.libs.json.{Json, Writes}

case class VotingQueue(
  id: Long,
  start: ZonedDateTime,
  finish: ZonedDateTime,
  capacity: Int = 1,
  voterGroups: Vector[VoterGroup]
)

object VotingQueue {

  implicit val writes: Writes[VotingQueue] = Json.writes[VotingQueue]
}

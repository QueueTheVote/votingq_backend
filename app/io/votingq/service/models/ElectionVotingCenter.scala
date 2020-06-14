package io.votingq.service.models

import java.time.ZonedDateTime

import play.api.libs.json.{Json, Writes}

case class PollingHours(
  start: String,
  finish: String
)

object PollingHours {
  implicit val writes: Writes[PollingHours] = Json.writes[PollingHours]
}

case class ElectionVotingCenter(
  id: Long,
  name: String,
  address: Address,
  pollingHours: Vector[PollingHours],
  election: Option[Election],
  availableQueues: Vector[VotingQueue],
  currentQueue: Option[VotingQueue]
)

object ElectionVotingCenter {
  implicit val writes: Writes[ElectionVotingCenter] = Json.writes[ElectionVotingCenter]
}

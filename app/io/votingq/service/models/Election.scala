package io.votingq.service.models

import java.time.LocalDate

import play.api.libs.json.{Json, Writes}

case class Election(
  id: Long,
  name: String,
  day: LocalDate,
  expired: Boolean = false
)

object Election {
  implicit val writes: Writes[Election] = Json.writes[Election]
}

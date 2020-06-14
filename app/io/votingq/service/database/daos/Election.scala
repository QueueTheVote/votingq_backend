package io.votingq.service.database.daos

import java.time.{Instant, LocalDate}

case class Election(
  id: Long,
  name: String,
  day: LocalDate,
  ocdDivisionId: String,
  expired: Boolean
)

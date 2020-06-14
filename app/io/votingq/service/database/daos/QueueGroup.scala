package io.votingq.service.database.daos

import java.time.Instant

case class QueueGroup(
  id: Long,
  electionId: Long,
  queueId: Long,
  centerId: Long,
  position: Int,
  queueEntered: Instant,
  queueLeft: Instant
)

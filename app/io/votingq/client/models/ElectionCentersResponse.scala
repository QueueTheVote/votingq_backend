package io.votingq.client.models

import io.votingq.service.models.{Election, ElectionVotingCenter}

case class ElectionCentersResponse(
  election: Election,
  centers: Vector[ElectionVotingCenter]
)



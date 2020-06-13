package client.models

import service.models.{Election, ElectionVotingCenter}

case class ElectionCentersResponse(
  election: Election,
  centers: Vector[ElectionVotingCenter]
)



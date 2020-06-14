package io.votingq.service

import io.votingq.client.models.CentersRequest
import io.votingq.service.models._

class CentersService {

  import DummyDatabase._

  /**
   * Gets centers from election id and address
   *
   * @return Dummy response
   */
  def getCenters(request: CentersRequest): Vector[ElectionVotingCenter] = {
    electionVotingCenters.asVector.filter(_.election.exists(_.id == request.electionId))
  }

  def getCenter(centerId: Long): Option[ElectionVotingCenter] = electionVotingCenters.asMap.get(centerId)
}

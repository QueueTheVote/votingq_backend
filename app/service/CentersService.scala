package service

import java.time.{LocalDate, ZoneId, ZonedDateTime}

import client.models.CentersRequest
import service.models._

class CentersService {

  import CentersService._

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

/**
 * Currently functioning as dummy database
 */
object CentersService {

  private val zoneId = ZoneId.of("-07:00")

  val election: Election = Election(
    id = 4977,
    name = "Colorado State Primary Election",
    day = LocalDate.of(2020, 6, 14)
  )
  val start: ZonedDateTime = election.day.atTime(8, 0).atZone(zoneId)
  val finish: ZonedDateTime = election.day.atTime(19, 0).atZone(zoneId)

  object votingQueues {

    def minusDays(days: Int): (ZonedDateTime, ZonedDateTime) = (start.minusDays(days), finish.minusDays(days))

    val queue: VotingQueue = VotingQueue(
      id = 1,
      start = start,
      finish = finish,
      voterGroups = Vector.empty
    )
    val queue3DaysEarly: VotingQueue = {
      val (st, fin) = minusDays(3)
      queue.copy(id = 2, start = st, finish = fin)
    }
    val queue2DaysEarly: VotingQueue = {
      val (st, fin) = minusDays(2)
      queue.copy(id = 3, start = st, finish = fin)
    }
    val queue1DayEarly: VotingQueue = {
      val (st, fin) = minusDays(1)
      queue.copy(id = 4, start = st, finish = fin)
    }
    val asVector: Vector[VotingQueue] = Vector(queue3DaysEarly, queue2DaysEarly, queue1DayEarly, queue)
  }

  val hours: PollingHours = PollingHours(
    start = start,
    finish = finish
  )

  object electionVotingCenters {

    val v1: ElectionVotingCenter = ElectionVotingCenter(
      id = 1,
      name = "Chapel Hills Mall",
      election = Some(election),
      address = Address(
        street1 = "1910 Briargate Boulevard",
        street2 = None,
        city = "Colorado Springs",
        state = "CO",
        zip = "80920"
      ),
      availableQueues = Vector(votingQueues.queue),
      currentQueue = Some(votingQueues.queue),
      pollingHours = Vector(hours)
    )
    val v2: ElectionVotingCenter = ElectionVotingCenter(
      id = 2,
      name = "Vista Grande Baptist Church",
      election = Some(election),
      address = Address(
        street1 = "5680 Stetson Hills Boulevard",
        street2 = None,
        city = "Colorado Springs",
        state = "CO",
        zip = "80917"
      ),
      availableQueues = Vector(votingQueues.queue),
      currentQueue = Some(votingQueues.queue),
      pollingHours = Vector(hours)
    )
    val v3: ElectionVotingCenter = ElectionVotingCenter(
      id = 3,
      name = "EPC Clerk's Office North Branch",
      election = Some(election),
      address = Address(
        street1 = "8830 North Union Boulevard",
        street2 = Some("Ste 505"),
        city = "Colorado Springs",
        state = "CO",
        zip = "80920"
      ),
      availableQueues = Vector(votingQueues.queue),
      currentQueue = Some(votingQueues.queue),
      pollingHours = Vector(hours)
    )
    val asVector: Vector[ElectionVotingCenter] = Vector(v1, v2, v3)
    val asMap: Map[Long, ElectionVotingCenter] = Map(v1.id -> v1, v2.id -> v2, v3.id -> v3)
  }

}

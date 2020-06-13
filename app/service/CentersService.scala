package service

import java.time.{LocalDate, ZoneId, ZonedDateTime}

import client.models.CentersRequest
import service.models._

class CentersService {

  import CentersService._

  /**
   * Gets centers with polling hours from election id and address
   *
   * @return Dummy response
   */
  def getCenters(request: CentersRequest): Vector[ElectionVotingCenter] = electionVotingCenters
}

object CentersService {

  private val zoneId = ZoneId.of("America/New_York")

  val election: Election = Election(
    id = 2000,
    name = "VIP Test Election",
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
      capacity = 1,
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
  val electionVotingCenters: Vector[ElectionVotingCenter] = Vector(
    ElectionVotingCenter(
      id = 1,
      name = "My Unavailable Voting Center",
      election = Some(election),
      address = Address(
        street1 = "123 Baker St",
        street2 = None,
        city = "New York",
        state = "NY",
        zip = "11014"
      ),
      availableQueues = Vector.empty,
      currentQueue = None,
      pollingHours = Vector.empty
    ),
    ElectionVotingCenter(
      id = 2,
      name = "My Single-Day Available Voting Center",
      election = Some(election),
      address = Address(
        street1 = "754 Shaw Ave",
        street2 = None,
        city = "New York",
        state = "NY",
        zip = "11014"
      ),
      availableQueues = Vector(votingQueues.queue),
      currentQueue = Some(votingQueues.queue),
      pollingHours = Vector(hours)
    ),
    ElectionVotingCenter(
      id = 3,
      name = "My Multi-Day Available Voting Center",
      election = Some(election),
      address = Address(
        street1 = "754 Puppy Rd",
        street2 = Some("Ste 505"),
        city = "New York",
        state = "NY",
        zip = "11014"
      ),
      availableQueues = votingQueues.asVector,
      currentQueue = Some(votingQueues.queue3DaysEarly),
      pollingHours = Vector(
        hours.copy(start = hours.start.minusDays(3)),
        hours.copy(start = hours.start.minusDays(2)),
        hours.copy(start = hours.start.minusDays(1)),
        hours
      )
    ),
  )
}

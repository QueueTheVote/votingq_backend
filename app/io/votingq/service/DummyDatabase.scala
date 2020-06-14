package io.votingq.service

import java.time.{LocalDate, ZoneId, ZonedDateTime}

import io.votingq.service.models.{Address, Election, ElectionVotingCenter, PollingHours, Voter, VoterGroup, VotingQueue}

/**
 * Currently functioning as dummy database
 */
object DummyDatabase {

  private val zoneId = ZoneId.of("-07:00")

  val election: Election = Election(
    id = 4977,
    name = "Colorado State Primary Election",
    day = LocalDate.of(2020, 6, 14)
  )
  val start: ZonedDateTime = election.day.atTime(8, 0).atZone(zoneId)
  val finish: ZonedDateTime = election.day.atTime(19, 0).atZone(zoneId)

  object voters {
    val v1: Voter = Voter(
      id = 1,
      email = "hello@example.com",
      name = "Andreya Triana"
    )
    val v2: Voter = Voter(
      id = 2,
      email = "hello2@example.com",
      name = "Melissa Viviane Jefferson"
    )
    val v3: Voter = Voter(
      id = 3,
      email = "hello3@example.com",
      name = "Janelle Monae"
    )
    val asMap: Map[Long, Voter] = Map(v1.id -> v1, v2.id -> v2, v3.id -> v3)
  }

  object voterGroups {
    val v1: VoterGroup = VoterGroup(position = 1, voters = Vector(voters.v2))
  }

  object votingQueues {

    def minusDays(days: Int): (ZonedDateTime, ZonedDateTime) = (start.minusDays(days), finish.minusDays(days))

    val queue1: VotingQueue = VotingQueue(
      id = 1,
      start = start,
      finish = finish,
      voterGroups = Vector.empty
    )
    val queue2: VotingQueue = queue1.copy(id = 2)
    val queue3: VotingQueue = queue1.copy(id = 3)
    val queue3DaysEarly: VotingQueue = {
      val (st, fin) = minusDays(3)
      queue1.copy(id = 4, start = st, finish = fin)
    }
    val queue2DaysEarly: VotingQueue = {
      val (st, fin) = minusDays(2)
      queue1.copy(id = 5, start = st, finish = fin)
    }
    val queue1DayEarly: VotingQueue = {
      val (st, fin) = minusDays(1)
      queue1.copy(id = 6, start = st, finish = fin)
    }
    val asMap: Map[Long, VotingQueue] = Map(
      queue3DaysEarly.id -> queue3DaysEarly,
      queue2DaysEarly.id -> queue2DaysEarly,
      queue1DayEarly.id -> queue1DayEarly,
      queue1.id -> queue1,
      queue2.id -> queue2,
      queue3.id -> queue3)
  }

  val hours: PollingHours = PollingHours(
    start = "Sat. Jun. 14 8:00 a.m.",
    finish = "7:00 p.m. MT"
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
      availableQueues = Vector(votingQueues.queue1),
      currentQueue = Some(votingQueues.queue1),
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
      availableQueues = Vector(votingQueues.queue2),
      currentQueue = Some(votingQueues.queue2),
      pollingHours = Vector(hours.copy(finish = "5:00 p.m. MT"))
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
      availableQueues = Vector(votingQueues.queue3),
      currentQueue = Some(votingQueues.queue3),
      pollingHours = Vector(hours.copy(start = "Sat. Jun. 14 9:00 a.m."))
    )
    val asVector: Vector[ElectionVotingCenter] = Vector(v1, v2, v3)
    val asMap: Map[Long, ElectionVotingCenter] = Map(v1.id -> v1, v2.id -> v2, v3.id -> v3)
  }

}

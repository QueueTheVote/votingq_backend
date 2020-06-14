package io.votingq.service

import java.time.ZonedDateTime

import io.votingq.service.models.{Voter, VoterGroup, VotingQueue}

import scala.util.{Failure, Success, Try}

class QueueService {

  import DummyDatabase._

  def joinQueue(queueId: Long, voterIds: Set[Long]): Either[QueueFailure, VotingQueue] = {
    votingQueues.asMap.get(queueId).map { queue =>
      val queueSize = queue.voterGroups.size
      val newGroupSize = voterIds.size
      if (queue.voterGroups.contains(voterIds)) {
        Left(AlreadyInQueue(voterIds))
      }
      else if (queue.finish.isBefore(ZonedDateTime.now(queue.finish.getZone))) {
        Left(QueueExpired(queue.finish))
      } else if (queue.capacity == queueSize) {
        Left(QueueMaxCapacityReached(queue.capacity))
      } else if (queue.capacity < queueSize + newGroupSize) {
        Left(QueueOverCapacity(queue.capacity, newGroupSize, queueSize))
      } else {
        // Todo: this is awful
        voterIds.find(id => !voters.asMap.contains(id))
          .map(id => Left(VoterNotExists(id)))
          .getOrElse {
            val newVoters = voterIds.flatMap(id => voters.asMap.get(id)).toVector
            val newPosition = queueSize + 1
            val newGroup = VoterGroup(position = newPosition, voters = newVoters)
            Right(queue.copy(voterGroups = queue.voterGroups :+ newGroup))
          }
      }
    }.getOrElse(Left(QueueNotExists(queueId)))
  }
}

sealed trait QueueFailure {
  def msg: String
}

sealed trait BadQueueRequest extends QueueFailure

case class AlreadyInQueue(voterIds: Set[Long]) extends QueueFailure {

  override val msg: String = s"Cannot join queue: one or more voterIds already in queue=$voterIds"
}

case class QueueMaxCapacityReached(capacity: Int) extends QueueFailure {

  override val msg: String = s"Cannot join queue: max capacity already reached=$capacity"
}

case class QueueOverCapacity(capacity: Int, groupSize: Int, queueSize: Int) extends QueueFailure {

  override val msg: String = s"Cannot join queue: voter group size=$groupSize will exceed queue capacity=$capacity with existing queue size=$queueSize"
}

case class QueueExpired(finish: ZonedDateTime) extends QueueFailure {

  override val msg: String = s"Cannot join queue: open time window has passed=$finish"
}

case class QueueNotExists(id: Long) extends QueueFailure {

  override val msg: String = s"Cannot join queue: queue with given id=$id does not exist."
}

// Todo: this doesn't belong here
case class VoterNotExists(id: Long) extends QueueFailure {
  override val msg: String = s"Cannot join queue: voter with voterId=$id does not exist."
}

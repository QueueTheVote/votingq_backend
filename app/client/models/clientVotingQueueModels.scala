package client.models

import java.time.ZonedDateTime

import play.api.libs.json.{Json, Writes}
import service.models.{Voter, VotingQueue}

sealed trait ClientVotingQueue {

  def id: Long

  def start: ZonedDateTime

  def finish: ZonedDateTime

  def capacity: Int

  def size: Int
}

case class MinimalVotingQueue(
  override val id: Long,
  override val start: ZonedDateTime,
  override val finish: ZonedDateTime,
  override val capacity: Int,
  override val size: Int,
) extends ClientVotingQueue

object MinimalVotingQueue {

  def apply(queue: VotingQueue): MinimalVotingQueue = MinimalVotingQueue(
    queue.id,
    queue.start,
    queue.finish,
    queue.capacity,
    queue.voterGroups.size
  )

  implicit val writes: Writes[MinimalVotingQueue] = Json.writes[MinimalVotingQueue]
}

case class CurrentUserVotingQueue(
  override val id: Long,
  override val start: ZonedDateTime,
  override val finish: ZonedDateTime,
  override val capacity: Int,
  override val size: Int,
  position: Int,
  voters: Vector[Voter]
) extends ClientVotingQueue

object CurrentUserVotingQueue {

  def fromVoterId(
    queue: VotingQueue,
    voterGroupId: Long
  ): Option[CurrentUserVotingQueue] = {
    queue.voterGroups
      .find(groups => groups.voters.exists(_.id == voterGroupId))
      .map { group =>
        CurrentUserVotingQueue(
          id = queue.id,
          start = queue.start,
          finish = queue.finish,
          capacity = queue.capacity,
          size = queue.voterGroups.size,
          position = group.position,
          voters = group.voters
        )
      }
  }

  implicit val writes: Writes[CurrentUserVotingQueue] = Json.writes[CurrentUserVotingQueue]
}

object ClientVotingQueue {
  implicit val writes: Writes[ClientVotingQueue] = Writes[ClientVotingQueue] {
    case r: CurrentUserVotingQueue => Json.writes[CurrentUserVotingQueue].writes(r)
    case r: MinimalVotingQueue => Json.writes[MinimalVotingQueue].writes(r)
  }
}

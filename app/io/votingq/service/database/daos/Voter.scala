package io.votingq.service.database.daos

case class Voter(
  id: Long,
  name: String,
  email: String,
  verified: Boolean
)

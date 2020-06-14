package io.votingq.service.database.daos

case class Center(
  id: Long,
  name: String,
  street1: String,
  street2: Option[String],
  city: String,
  state: String,
  zip: String
)

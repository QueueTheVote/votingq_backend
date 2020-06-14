package io.votingq.client.models

case class CentersRequest(
  electionId: Long,
  street1: String,
  street2: Option[String],
  city: String,
  state: String,
  zip: Option[String]
)

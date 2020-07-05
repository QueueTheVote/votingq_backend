package io.votingq.service

import cats.effect.{Blocker, IO}
import cats.implicits._
import com.typesafe.scalalogging.StrictLogging
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts

import scala.util.{Properties, Try}

class VotingQDataSource extends StrictLogging {
  import VotingQDataSource._

  def run(): Unit = {
    val program1: ConnectionIO[Int] = 42.pure[ConnectionIO]
    // We need a ContextShift[IO] before we can construct a Transactor[IO]. The passed ExecutionContext
    // is where nonblocking operations will be executed. For testing here we're using a synchronous EC.
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
    // on an our synchronous EC. See the chapter on connection handling for more info.
    val xa = Transactor.fromDriverManager[IO](
      driver = EnvironmentVariables.databaseDriver,
      url = EnvironmentVariables.databaseUrl,
      user = EnvironmentVariables.databaseUsername,
      pass = EnvironmentVariables.databasePassword,
      // for Testing
      blocker = Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )
    sql"select name from centers"
      .query[String]
      .to[List]
      .transact(xa)
      .unsafeRunSync()
      .foreach(a => logger.warn(s"Center: $a"))
    val io = program1.transact(xa)
    // io: IO[Int] = Async(
    //   cats.effect.internals.IOBracket$$$Lambda$8180/248688506@6db044f4,
    //   false
    // )
    io.unsafeRunSync
    // res0: Int = 42
  }
}

object VotingQDataSource {
  private object EnvironmentVariables {
    val databaseDriver: String = "org.postgresql.Driver"
    // "_REAL" is to get around inability to overwrite "DATABASE_URL" on Heroku
    val databaseUrl: String = Properties.envOrElse("DATABASE_URL_REAL", "jdbc:postgresql://localhost:5432/voting_q")
    val databaseUsername: String = Properties.envOrElse("DATABASE_USERNAME", "queue_backend_user")
    val databasePassword: String = Properties.envOrElse("DATABASE_PASSWORD", "")
  }
}

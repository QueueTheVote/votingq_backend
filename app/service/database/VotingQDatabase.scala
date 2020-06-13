package service.database

import javax.inject.Inject
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future}

class VotingQDatabase @Inject()(
  @NamedDatabase("voting_q") database: Database,
  databaseExecutionContext: ExecutionContext
) {

  def updateSomething(): Unit = {
    Future {
      database.withConnection { conn =>
        // do whatever you need with the db connection
      }
    }(databaseExecutionContext)
  }
}

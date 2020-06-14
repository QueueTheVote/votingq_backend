package client.controllers

import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class VoterController @Inject()(
  val controllerComponents: ControllerComponents
) extends BaseController {

}


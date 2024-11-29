package fr.cytech.icc

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.*
import scala.language.postfixOps
import scala.util.{ Failure, Success }

import org.apache.pekko.actor
import org.apache.pekko.actor.typed.{ ActorSystem, Scheduler }
import org.apache.pekko.actor.typed.scaladsl.{ ActorContext, Behaviors }
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.util.Timeout
import org.slf4j.{ Logger, LoggerFactory }

object Main {

  private def run() = Behaviors.setup[Nothing] { context =>
    given actor.ActorSystem = context.system.classicSystem
    given logger: Logger = LoggerFactory.getLogger("HTTP Server")

    given ExecutionContext = context.executionContext

    given Timeout = 10 seconds

    given Scheduler = context.system.scheduler

    val rooms = context.spawn(
      RoomListActor(
        Map("toto" -> context.spawn(RoomActor("toto"), "toto"))
      ),
      "RoomListActor"
    )
    val routes = Controller(rooms).routes

    Http().newServerAt("localhost", 8080).bind(routes).onComplete {
      case Failure(exception) => logger.error(exception.getMessage)
      case Success(binding) =>
        logger.info(s"Server now online. Please navigate to http:/${binding.localAddress.toString}")
    }

    Behaviors.ignore
  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem[Nothing](run(), "TPActors")
  }
}

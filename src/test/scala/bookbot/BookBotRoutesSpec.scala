package bookbot

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._

import org.scalatest._
import bookbot.server.PingHandler
import bookbot.server.BookBotRoutes
import org.http4s.util.CaseInsensitiveString
import org.http4s.headers.{`Content-Type`}

class BookBotRoutesSpec extends FunSpec with Matchers{

  describe("pingRoutes") {
    it("returns 200") {
      assert(makeRequest.status equals (Status.Ok))
    }

    it("returns 'pingback' in the message") {
      assert(makeRequest.as[String].unsafeRunSync() contains ("pingback"))
    }

    it("returns json content") {
      val contentType = makeRequest.headers.get(`Content-Type`).get.value
      assert(contentType equals "application/json")
    }
  }

  private[this] val makeRequest: Response[IO] = {
    val get = Request[IO](Method.POST, uri"/hello")
    val ping = PingHandler.impl[IO]
    BookBotRoutes.pingRoutes(ping).orNotFound(get).unsafeRunSync()
  }
}
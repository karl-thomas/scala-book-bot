package bookbot

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._

import org.scalatest._
import bookbot.server.SlackHandler
import bookbot.server.BookBotRoutes
import org.http4s.util.CaseInsensitiveString
import org.http4s.headers.{`Content-Type`}
import cats.data.Chain
import org.http4s.client.dsl.io._
import org.http4s.Method._

class BookBotRoutesSpec extends FunSpec with Matchers {

  describe("slackRoutes") {
    describe("on receiving a ping command") {
      it("returns 200") {
        assert(makeRequest("ping").status equals (Status.Ok))
      }

      it("returns 'pingback' in the message") {
        assert(makeRequest("ping").as[String].unsafeRunSync() contains ("pingback"))
      }

      it("returns json content") {
        val contentType = makeRequest("ping").headers.get(`Content-Type`).get.value
        assert(contentType equals "application/json")
      }
    }
  }

  describe("getSlackText") {
    it("should return the value of the 'text' property in a decoded urlform") {
      BookBotRoutes.getSlackText(UrlForm("text" -> "hello")) shouldBe "hello"
    }
  }

  private[this] def makeRequest(text: String): Response[IO] = {
    val request = Request[IO](Method.POST, uri"/hello").withEntity(UrlForm("text" -> text))
    val slack = SlackHandler.impl[IO]
    BookBotRoutes.slackRoutes(slack).orNotFound(request).unsafeRunSync()
  }
}
package bookbot.server

import cats.Applicative
import cats.implicits._
import bookbot.models.MessageToSlackChannel
import org.http4s.Request
import org.http4s.EntityDecoder
import org.http4s.Charset.`UTF-8`
import org.http4s.UrlForm
import cats._, cats.effect._, cats.implicits._, cats.data._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._


trait PingHandler[F[_]]{
  def pingback: F[MessageToSlackChannel]
}

object PingHandler {
  implicit def apply[F[_]](implicit ev: PingHandler[F]): PingHandler[F] = ev
  def impl[F[_]: Applicative]: PingHandler[F] = new PingHandler[F]{
    def pingback: F[MessageToSlackChannel] = {
      MessageToSlackChannel("pingback").pure[F]
    }
  }
}
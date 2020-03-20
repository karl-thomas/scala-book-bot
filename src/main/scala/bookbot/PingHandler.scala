package bookbot.server

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._
import bookbot.models.PingBack

trait PingHandler[F[_]]{
  def pingback(): F[PingBack]
}

object PingHandler {
  implicit def apply[F[_]](implicit ev: PingHandler[F]): PingHandler[F] = ev
  
  def impl[F[_]: Applicative]: PingHandler[F] = new PingHandler[F]{
    def pingback: F[PingBack] = PingBack("pingback").pure[F]
  }
}
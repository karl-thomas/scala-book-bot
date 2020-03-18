package bookbot.server

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait PingHandler[F[_]]{
  def pingback(): F[PingHandler.PingBack]
  // def pingback(): F[PingHandler.Greeting]
}

object PingHandler {
  implicit def apply[F[_]](implicit ev: PingHandler[F]): PingHandler[F] = ev
  /**
    * More generally you will want to decouple your edge representations from
    * your internal data structures, however this shows how you can
    * create encoders for your data.
    **/
  final case class PingBack(message: String) extends AnyVal
  object PingBack {
    implicit val pingEncoder: Encoder[PingBack] = new Encoder[PingBack] {
      final def apply(a: PingBack): Json = Json.obj(
        ("response_type", Json.fromString("in_channel")),
        ("text", Json.fromString(a.message)),
      )
    }
    implicit def pingEntityEncoder[F[_]: Applicative]: EntityEncoder[F, PingBack] =
      jsonEncoderOf[F, PingBack]
  }

  def impl[F[_]: Applicative]: PingHandler[F] = new PingHandler[F]{
    def pingback: F[PingHandler.PingBack] = PingBack("pingback").pure[F]
  }
}
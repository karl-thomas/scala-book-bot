package bookbot.models

import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._
import cats.Applicative

final case class PingBack(message: String) extends AnyVal
object PingBack {
  implicit val pingEncoder: Encoder[PingBack] = new Encoder[PingBack] {
    final def apply(a: PingBack): Json = Json.obj(
      ("response_type", Json.fromString("in_channel")),
      ("text", Json.fromString(a.message)),
    )
  }
  
  implicit def pingEntityEncoder[F[_]: Applicative]: EntityEncoder[F, PingBack] = jsonEncoderOf[F, PingBack]
}
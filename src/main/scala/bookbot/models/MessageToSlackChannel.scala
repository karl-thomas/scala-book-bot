package bookbot.models

import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._
import cats.Applicative

final case class MessageToSlackChannel(message: String) extends AnyVal
object MessageToSlackChannel {
  implicit val pingEncoder: Encoder[MessageToSlackChannel] = new Encoder[MessageToSlackChannel] {
    final def apply(a: MessageToSlackChannel): Json = Json.obj(
      ("response_type", Json.fromString("in_channel")),
      ("text", Json.fromString(a.message)),
    )
  }
  
  implicit def pingEntityEncoder[F[_]: Applicative]: EntityEncoder[F, MessageToSlackChannel] = jsonEncoderOf[F, MessageToSlackChannel]
}
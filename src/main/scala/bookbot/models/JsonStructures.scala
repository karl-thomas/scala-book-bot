package bookbot.models
import io.circe._, io.circe.generic.semiauto._

case class Volume(volumeInfo: VolumeInfo)
object Volume {
  implicit val decoder: Decoder[Volume] = deriveDecoder[Volume]
  implicit val encoder: Encoder[Volume] = deriveEncoder[Volume]
}

case class VolumeInfo(industryIdentifiers: Option[List[IndustryIdentifier]])
object VolumeInfo {
  implicit val decoder: Decoder[VolumeInfo] = deriveDecoder[VolumeInfo]
  implicit val encoder: Encoder[VolumeInfo] = deriveEncoder[VolumeInfo]
}

case class IndustryIdentifier(`type`: String, identifier: String)
object IndustryIdentifier {
  implicit val decoder: Decoder[IndustryIdentifier] = deriveDecoder[IndustryIdentifier]
  implicit val encoder: Encoder[IndustryIdentifier] = deriveEncoder[IndustryIdentifier]
}

case class GoogleResponse(items: List[Volume])
object GoogleResponse {
  implicit val decoder: Decoder[GoogleResponse] = deriveDecoder[GoogleResponse]
  implicit val encoder: Encoder[GoogleResponse] = deriveEncoder[GoogleResponse]
}
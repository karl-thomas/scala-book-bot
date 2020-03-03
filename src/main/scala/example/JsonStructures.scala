package example

case class GoogleResponse(items: List[Volume])
case class Volume(volumeInfo: VolumeInfo)
case class VolumeInfo(title: String, authors: List[String], industryIdentifiers: List[IndustryIdentifier])
case class IndustryIdentifier(`type`: String, identifier: String)

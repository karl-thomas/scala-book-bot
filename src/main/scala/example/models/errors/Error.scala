package example.models.errors

trait Error {
  def getMessage: String
}

case class TransformError(message: String) extends Error {
  def getMessage: String = message
}

case class HttpError(message: String) extends Error {
  def getMessage: String = message
}
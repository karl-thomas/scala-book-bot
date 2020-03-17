package example.models.errors

import cats.data.Kleisli

trait Error {
  def getMessage: String
}

object Error {
  type ErrorOr[A] = Either[Error, A]
}

case class TransformError(message: String) extends Error {
  def getMessage: String = message
}

case class HttpError(message: String) extends Error {
  def getMessage: String = message
}
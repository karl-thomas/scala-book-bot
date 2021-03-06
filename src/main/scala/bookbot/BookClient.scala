package bookbot

import scalaj.http.{HttpRequest, HttpResponse}
import bookbot.models.errors.HttpError
import scalaj.http.Http
import models.errors.Error.ErrorOr

object BookService {
  type HttpFunction = String => HttpRequest

  def get(http: HttpFunction)(title: String = "", author: String = ""): ErrorOr[String] = {
    val response: HttpResponse[String] = http(sys.env.getOrElse("API_URL", ""))
      .param("key", sys.env.getOrElse("GOOGLE_API_KEY", ""))
      .param("q", s"${title}+${author}")
      .asString

    response.code match {
      case 200 => Right(response.body)
      case code => Left(HttpError(s"Search request failed with a $code error code"))
    }
  }
}
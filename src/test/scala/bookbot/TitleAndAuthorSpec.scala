package bookbot

import org.scalatest.FunSpec
import org.scalatest.Matchers
import bookbot.models.TitleAndAuthor

class TitleAndAuthorSpec extends FunSpec with Matchers {
  describe("TitleAndAuthor") {
    val title = "Hello There"
    val author = "I wrote this!"
    describe("fromRawString") {
      it("should return a title and author when passed a string") {
        TitleAndAuthor.from(s"$title by $author").getOrElse("oh no") shouldBe(title -> author)
      }

      it ("should return just a title if there is nothing after 'by'") {
        TitleAndAuthor.from(s"$title by").getOrElse("oh no") shouldBe(title -> "")
      }

      it ("should return the author if there is nothing before 'by'") {
        TitleAndAuthor.from(s"by $author").getOrElse("oh no") shouldBe("" -> author)
      }

      it ("should return the search term as the title if there is no 'by'") {
        val searchString = s"$title $author"
        TitleAndAuthor.from(searchString).getOrElse("oh no") shouldBe(searchString -> "")
      }
    }
  }
}
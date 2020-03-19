package com.example.server

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]) =
    BookBotServer.stream[IO].compile.drain.as(ExitCode.Success)
}
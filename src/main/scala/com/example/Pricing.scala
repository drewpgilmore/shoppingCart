package com.example

import cats.effect.{IO, IOApp, ExitCode}
import io.circe.generic.auto._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{Method, Request, Uri}
import org.http4s.circe.jsonOf

import com.example.Main.Item

object HttpClient {
  def decodeItem(name: String): IO[Item] = {
    val baseUrl = "https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main"
    val req = Request[IO](Method.GET, Uri.unsafeFromString(s"$baseUrl/$name.json"))
    val emberClient = EmberClientBuilder.default[IO].build
    emberClient.use {
      httpClient => httpClient.expect(req)(jsonOf[IO, Item])
    }
  }
}

object TestPrice extends IOApp {
  val itemName = "cornflakes"
  val item: IO[Item] = HttpClient.decodeItem(itemName)
  val x = 3
  //val price: IO[Double] = item
  def run(args: List[String]): IO[ExitCode] = ???
  //val run: IO[Unit] = IO(println(price))
}
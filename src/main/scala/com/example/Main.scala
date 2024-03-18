package com.example

import cats.effect.{IO, IOApp}
import cats.effect.implicits._
import cats.syntax.all._
import io.circe.generic.auto._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.client.Client
import org.http4s.{Method, Request, Uri}
import org.http4s.circe.jsonOf

object Main extends IOApp.Simple {
  val baseUrl = "https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main"  
  
  case class Item(title: String, price: Double)
  
  def decodeItem(title: String): IO[Item] = {
    val req = Request[IO](Method.GET, Uri.unsafeFromString(s"$baseUrl/$title.json"))
    val emberClient = EmberClientBuilder.default[IO].build
    emberClient.use {
      httpClient => httpClient.expect(req)(jsonOf[IO, Item])
    }
  }

  case class CartItem(item: IO[Item], var qty: Int) { 
    def getCost: IO[Double] = item.map(_.price * qty)
    
    def receiptLine: IO[String] = {
      item.flatMap(i => 
        for { 
          name <- IO(i.title)
          price <- IO(i.price)
          cost <- getCost
        } yield s"$qty x $name @ $price = $cost"
      )
    }

    def updateQty(newQty: Int) {
      qty = newQty
    }
  }

  case class Cart(items: List[CartItem]) {

    def addItem(title: String, qty: Int = 1) {
      val sameItem: List[CartItem] = items.filter(_.item.map(_.title) == title)
      if (sameItem.isEmpty) items :+ CartItem(decodeItem(title), qty)
      else sameItem.head.qty += qty // if item already in cart, update qty
    }

    def printTotal(): IO[Unit] = {
      items.traverse(i => i.receiptLine).map(println(_))
    }
  }

  def run: IO[Unit] = {
    // Add 2 × cornflakes @ 2.52 each
    // Add 1 × weetabix @ 9.98 each
    // Subtotal = 15.02
    // Tax = 1.88
    // Total = 16.90
    val cornflakes = CartItem(decodeItem("cornflakes"), 2) 
    val weetabix = CartItem(decodeItem("weetabix"), 1) 
    val ordered = List(cornflakes, weetabix)
    val cart = Cart(ordered)
    cart.printTotal()
  }
}
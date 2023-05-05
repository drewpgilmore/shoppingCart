package com.example

import cats.effect.{IO, IOApp}
import cats.effect.implicits._
import cats.syntax.all._
import HttpClient.decodeItem
import com.sun.tools.javac.jvm.Items

object Main extends IOApp.Simple {
  case class Item(title: String, price: Double)
  case class CartItem(item: IO[Item], qty: Int)
  //case class CartItem(item: Item, qty: Int)
  case class Cart(items: List[CartItem])

//  def addItem(cart: Cart, itemName: String, qty: Int): Cart = {
//    val updatedItems: List[CartItem] = {
//      cart.items :+ CartItem(HttpClient.decodeItem(itemName), qty)
//    }
//    Cart(updatedItems)
//  }
//
//  def cartTotal(cart: Cart): IO[Unit] = {
//    cart.items.traverse(i =>
//      IO((i.item).map(_.price * 0.125))
//    ).map(println(_))
//  }
//
//  class PurchasedItem[F[_]](name: String, qty: Int) {
//    val item: IO[Item] = HttpClient.decodeItem(name)
//    def cost[A, B](item: IO[Item])()
//  }

  def getPrice[A, B](item: IO[A])()
  def run: IO[Unit] = cartTotal(cart3)
}

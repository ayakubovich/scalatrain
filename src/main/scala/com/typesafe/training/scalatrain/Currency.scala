package com.typesafe.training.scalatrain

/**
 * Created by georgeli on 15-09-18.
 */
sealed abstract class Currency {
  def exchangeRate: Double
}

case class CAD(exchangeRate: Double = 1.0) extends Currency
case class USD(exchangeRate: Double = 10.0) extends Currency

case class Price(amount: Double, currency: Currency = CAD())

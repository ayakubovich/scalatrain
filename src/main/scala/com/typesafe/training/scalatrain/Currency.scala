package com.typesafe.training.scalatrain

import play.api.libs.json.JsNumber

/**
 * Created by georgeli on 15-09-18.
 */
sealed abstract class Currency

object Currency {

  import scala.concurrent.ExecutionContext.Implicits.global
  import com.ning.http.client.AsyncHttpClientConfig
  import play.api.libs.ws.ning.NingWSClient
  import play.api.libs.json._


  def parseExchange(jsonStr: String, currency: Currency): Double ={

    val json: JsValue = Json.parse(jsonStr)

    json \ "rates" \ currency.toString match {
      case JsNumber(number) => number.toDouble
    }
  }

  def exchangeRate(other: Currency): Double =  {

  val builder = new AsyncHttpClientConfig.Builder()
  val client  = new NingWSClient(builder.build())
    var toReturn = 0.0

  client.url("http://api.fixer.io/latest")
    .withQueryString("base" -> "CAD", "symbols" -> other.toString)
    .get
    .map(result => toReturn = parseExchange(result.body, other))
    .onComplete(t => client.close)

    toReturn
  }
}

case object CAD extends Currency{
  override  def toString = "CAD"
}
case object USD extends Currency{
  override  def toString = "USD"
}

case class Price(amount: Double, currency: Currency = CAD)

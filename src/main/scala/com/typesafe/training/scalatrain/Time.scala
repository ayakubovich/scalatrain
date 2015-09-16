
package com.typesafe.training.scalatrain

import play.api.libs.json.{Json, JsValue}
import scala.util.Try


case class Time(hours:Int = 0, minutes:Int = 0) extends Ordered[Time]{

  require(hours >= 0 & hours < 24, "Failed to meet requirement 0 < hours < 23")
  require(minutes >= 0 & minutes < 60, "Failed to meet requirement 0 < minutes < 60")

  val asMinutes: Int = hours * 60 + minutes

  override lazy val toString:String = f"$hours%02d:$minutes%02d"

  def minus(that:Time):Int = asMinutes - that.asMinutes

  def print() = println(hours.toString + ':' + minutes.toString)

  def -(that:Time):Int = minus(that)

  def compare(that: Time) = minus(that)

  def toJson: JsValue =
    Json.obj("hours" -> hours, "minutes" -> minutes)

}

object Time {
  def fromMinutes(minutes:Int):Time = new Time(minutes/60, minutes % 60)
  def fromJson(json: JsValue): Option[Time] = {
    val tryTime = for {
      hours <- Try((json \ "hours").as[Int])
      minutes <- Try((json \ "minutes").as[Int]).recover({ case _: Exception => 0 })
    } yield Time(hours, minutes)
    tryTime.toOption
  }

}
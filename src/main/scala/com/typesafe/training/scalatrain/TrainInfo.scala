package com.typesafe.training.scalatrain

import com.github.nscala_time.time.Imports._

abstract class TrainInfo {
  def number: Int
  def lastMaintained: Option[DateTime]
}

case class InterCityExpress(number:Int, lastMaintained:Option[DateTime] = None) extends TrainInfo
case class RegionalExpress(number:Int, hasWifi:Boolean = false, lastMaintained:Option[DateTime] = None) extends TrainInfo
case class BavarianRegional(number:Int, lastMaintained:Option[DateTime] = None) extends TrainInfo
package com.typesafe.training.scalatrain

abstract class TrainInfo {
  def number: Int
}

case class InterCityExpress(number:Int) extends TrainInfo
case class RegionalExpress(number:Int, hasWifi:Boolean = false) extends TrainInfo
case class BavarianRegional(number:Int) extends TrainInfo
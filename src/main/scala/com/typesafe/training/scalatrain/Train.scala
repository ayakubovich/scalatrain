package com.typesafe.training.scalatrain

import com.github.nscala_time.time.Imports._

case class Train(info:TrainInfo, schedule:Schedule, dist: Map[(Station,Station), Double]) {

  val stations: Seq[Station] = schedule.stations

  val distancePerDay: Double = dist.map(_._2).sum

  def price (station: Station):Double = schedule.price(station)

  def timeAt(station: Station): Option[Time] = schedule.timeAt(station)

  def genHop(from:Station, to: Station, departure: Time): Seq[Hop] =
    hops.dropWhile(hop => !(hop.from == from && hop.departure > departure)).
      reverse.dropWhile(_.to != to).reverse

  def backToBack: Seq[(Station, Station)] = stations.zip(stations.tail)

  def hops: Seq[Hop] = backToBack.map { case (s1,s2) => Hop(s1,s2, this) }

  def kms(from:Station, to:Station) = dist(from, to)

  def distanceSinceLastMaintained(dt:DateTime):Double = schedule.numDaysTravelledBetween2Dates(DateTime.now,dt) * distancePerDay
}

case class Station(name:String)
{
  override def toString() = name
}

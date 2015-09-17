package com.typesafe.training.scalatrain

case class Train(info:TrainInfo, schedule:Schedule) {

  val stations: Seq[Station] = schedule.stations

  def price (station: Station):Double = schedule.price(station)

  def timeAt(station: Station): Option[Time] = schedule.timeAt(station)

  def genHop(from:Station, to: Station, departure: Time): Seq[Hop] =
    hops.dropWhile(hop => !(hop.from == from && hop.departure > departure)).
      reverse.dropWhile(_.to != to).reverse


  def backToBack: Seq[(Station, Station)] = stations.zip(stations.tail)

  def hops: Seq[Hop] = backToBack.map { case (s1,s2) => Hop(s1,s2, this) }
}

case class Station(name:String)
{
  override def toString() = name
}

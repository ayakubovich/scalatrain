package com.typesafe.training.scalatrain

/**
 * Created by georgeli on 15-09-16.
 */
case class Hop (from: Station, to: Station, train: Train) {
  require(train.stations.contains(from))
  require(train.stations.contains(to))

  val departure: Time= train.timeAt(from).get
  //val departure: Time= train.schedule.find(_._2== from).headOption.getOrElse(throw new IllegalArgumentException)

  val arrival: Time= train.timeAt(to).get

  require(departure < arrival)

  val price = train.price(from)

  //override lazy val toString = from.toString + "-" + to.toString
  override lazy val toString =  s"${from.toString} -${to.toString}"
}

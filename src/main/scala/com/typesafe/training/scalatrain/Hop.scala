package com.typesafe.training.scalatrain

/**
 * Created by georgeli on 15-09-16.
 */
case class Hop (from: Station, to: Station, train: Train) {
  require(train.stations.contains(from))
  require(train.stations.contains(to))

  val departure: Time= train.timeAt(from).get

  val arrival: Time= train.timeAt(to).get

}

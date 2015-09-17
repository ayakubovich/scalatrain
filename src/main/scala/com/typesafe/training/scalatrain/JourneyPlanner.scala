package com.typesafe.training.scalatrain

class JourneyPlanner(val trains:Set[Train]){
  val stations = trains.flatMap(_.stations)

  // return all the trains that stop at a given station
  def trainsAt(station:Station) =
    trains.filter(_.stations.contains(station))

  def isShortTrip(from:Station, to:Station):Boolean ={
    // Return true if there exists a Train in trains where stations contain from and to
    // with at most one other Station in between
    trains.exists(train =>
      train.stations.dropWhile(station => station != from) match {
      case `from` +: `to` +: _ => true
      case `from` +: _ +: `to` +: _ => true
      case _ => false
    }
    )
  }

  def stopsAt(station:Station):Set[(Time, Train)] =
    for {
      train <- trainsAt(station)
    }
    yield
    (train.timeAt(station).get, train)

  def plan(from: Station, to: Station, departure: Time): Set[Seq[Hop]] =
    trains.map(_.genHop(from, to , departure)).filterNot(_.isEmpty)

  val hops:Map[Station, Set[Hop]] = trains.flatMap(_.hops).groupBy(_.from)
}

object JourneyPlanner {

  def sortPathsByTime (paths: Set[Seq[Hop]]) = paths.toSeq.sortBy{ case path: Seq[Hop] => path(path.size-1).arrival - path(0).departure}

  def sortPathsByPrice (paths: Set[Seq[Hop]]) = paths.toSeq.sortBy{ case path: Seq[Hop] => path.map(_.price).sum}
}
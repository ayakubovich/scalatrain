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

  def stopsAt(station:Station):Set[Tuple2[Time, Train]] =
    for {
      train <- trainsAt(station)
      (time, st) <- train.schedule if st == station
    }
    yield
    (time, train)

  def plan(from: Station, to: Station, departure: Time) : Set[Train] = trains.filter(_.canSchedule(from, to, departure))

  val hops:Map[Station, Set[Hop]] = trains.flatMap(train => train.backToBack().map(
    p=> Hop(p._1,p._2, train)
  )).groupBy(_.from)
}
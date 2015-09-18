package com.typesafe.training.scalatrain
import com.github.nscala_time.time.Imports._
import org.joda.time.Days

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

  def listTrainsOnDate(dt:DateTime):Set[Train] = {
    trains.filter(x => (x.schedule.recurring contains dt.getDayOfWeek) &&
      !(x.schedule.exceptionDates contains dt))
  }

  def stopsAt(station:Station):Set[(Time, Train)] =
    for {
      train <- trainsAt(station)
    }
    yield
    (train.timeAt(station).get, train)

  def planByTime(from: Station, to: Station, departure: Time): Set[Seq[Hop]] =
    trains.map(_.genHop(from, to , departure)).filterNot(_.isEmpty)

  def planByDate(from: Station, to: Station, dt: DateTime): Set[Seq[Hop]] =
    listTrainsOnDate(dt).map(_.genHop(from, to , Time(0))).filterNot(_.isEmpty)

  val hops:Map[Station, Set[Hop]] = trains.flatMap(_.hops).groupBy(_.from)

  val sinkStations:Set[Station]= {
    val candidates = trains.map(_.stations.last)
    val negatives = trains.flatMap(_.stations.reverse.tail)
    candidates.diff(negatives)
  }
}

object JourneyPlanner {

  def pathDuration (path:Seq[Hop]):Double = path(path.size-1).arrival - path(0).departure

  def pathPrice (path:Seq[Hop]):Double = path.map(_.price).sum

  def findDiscount(daysUntilJourney: Int) = Double match {
    case _ if daysUntilJourney >= 14 => 1
    case _ if daysUntilJourney >  1 && daysUntilJourney < 14 => 1.5
    case _ if daysUntilJourney >= 0 && daysUntilJourney <=1  => 0.75
    case _ => -1 //TODO: make this throw an exception later
  }

  def pathPriceOnDate (path:Seq[Hop], journeyDate:DateTime):Double = {
    //TODO: verify that journeyDate is after current date
    val dayUntilJourney= Days.daysBetween(DateTime.now, journeyDate).getDays
    pathPrice(path) * findDiscount(dayUntilJourney)
  }

  def sortPathsByPrice (paths: Set[Seq[Hop]]) = paths.toSeq.sortBy{case path => pathPrice(path)}

  def sortPathsByTime (paths: Set[Seq[Hop]]) = paths.toSeq.sortBy{case path => pathDuration(path)}
}
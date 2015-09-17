package com.typesafe.training.scalatrain
import com.github.nscala_time.time.Imports._

case class Schedule (schedule:Seq[(Time, Station, Double)], recurring: Set[Int] = Set.empty, exceptionDate:Set[DateTime] = Set.empty){

  require(schedule.length > 1, "Required schedule to be of sequence of length > 2, got a sequence of length " + schedule.length)
  require(schedule.forall(_._3 >= 0), "Price can not be negative")

  require(schedule.map(_._1).sorted == schedule.map(_._1), "times should be sorted")

  require(schedule.map(_._2).distinct.size == schedule.map(_._2).size, "no cycle in overall path")

  require(recurring.forall(_ >= 0), "enum value cant be negative")

  val stations: Seq[Station] = schedule.map(_._2)

  def price (station: Station):Double = schedule.find(_._2 == station).get._3

  def timeAt(station: Station): Option[Time] =
    schedule.find(stop => stop._2 == station).map(found => found._1)
  
}

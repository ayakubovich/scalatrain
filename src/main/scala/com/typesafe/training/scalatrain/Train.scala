package com.typesafe.training.scalatrain

case class Train(info:TrainInfo, schedule:Seq[(Time, Station)]) {
  require(schedule.length > 1, "Required schedule to be of sequence of length > 2, got a sequence of length " + schedule.length)
  //TODO: verify schedule is strictly increasing in time

  val stations: Seq[Station] = schedule.map(_._2)

  def timeAt(station: Station): Option[Time] =
    schedule.find(stop => stop._2 == station).map(found => found._1)

}

case class Station(name:String)
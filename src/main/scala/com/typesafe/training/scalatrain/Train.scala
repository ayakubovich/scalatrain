package com.typesafe.training.scalatrain

case class Train(info:TrainInfo, schedule:Seq[(Time, Station)]) {
  require(schedule.length > 1, "Required schedule to be of sequence of length > 2, got a sequence of length " + schedule.length)
  //TODO: verify schedule is strictly increasing in time

  val stations: Seq[Station] = schedule.map(_._2)

  def timeAt(station: Station): Option[Time] =
    schedule.find(stop => stop._2 == station).map(found => found._1)

  def genHop(from:Station, to: Station, departure: Time): Seq[Hop] = {
    val fromIx = this.schedule.indexWhere(s => s._2 == from & s._1 > departure)
    val toIx = this.schedule.indexWhere(_._2 == to)
    if (fromIx != -1){
      hops.slice(fromIx, toIx)
    } else{
      Seq.empty[Hop]
    }
  }
    //hops.dropWhile(hop => !(hop.from == from && hop.departure > departure)).reverse.dropWhile(_.to != to).reverse

  def backToBack: Seq[(Station, Station)] = {
    val stations = schedule.map(_._2)
    stations.zip(stations.tail)
  }

  def hops: Seq[Hop] = backToBack.map { case (s1,s2) => Hop(s1,s2, this) }

}

case class Station(name:String)
{
  override def toString() = name
}

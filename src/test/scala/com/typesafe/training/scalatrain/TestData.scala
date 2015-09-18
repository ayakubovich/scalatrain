/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain
import com.github.nscala_time.time.Imports._
object TestData {

  val munich = Station("Munich")

  val nuremberg = Station("Nuremberg")

  val frankfurt = Station("Frankfurt")

  val cologne = Station("Cologne")

  val essen = Station("Essen")

  val ice724MunichTime = Time(8, 50)

  val ice724NurembergTime = Time(10)

  val ice724FrankfurtTime = Time(12, 10)

  val ice724CologneTime = Time(13, 39)

  val ice726MunichTime = Time(7, 50)

  val ice726NurembergTime = Time(9)

  val ice726FrankfurtTime = Time(11, 10)

  val ice726CologneTime = Time(13, 2)

  val dists = Map(
    (munich, nuremberg) -> 1.1,
    (munich, frankfurt) -> 2.2,
      (nuremberg, frankfurt) -> 10.1,
    (frankfurt, cologne) -> 100.1,
    (frankfurt, essen) -> 1000.1
  )
  val schedule724 = Schedule(Seq(
      (ice724MunichTime, munich, 10.0),
      (ice724NurembergTime, nuremberg, 5.0),
      (ice724FrankfurtTime, frankfurt, 5.0),
      (ice724CologneTime, cologne, 0.0)
    ), Set(1,2,3,4,5,6,7)
  )

  val ice724 = Train(
    InterCityExpress(724),
    schedule724,
  dists
  )


  val xmas = (new DateTime).withYear(2014).withMonthOfYear(12).withDayOfMonth(25) //nyd.getDayOfWeek = 4
  val nyd = (new DateTime).withYear(2014).withMonthOfYear(12).withDayOfMonth(31) //nyd.getDayOfWeek = 3
  val nye = (new DateTime).withYear(2015).withMonthOfYear(1).withDayOfMonth(1) //nyd.getDayOfWeek = 4

  val schedule726 = Schedule(Seq(
      (ice726MunichTime, munich, 15.0),
      (ice726NurembergTime, nuremberg,5.0),
      (ice726FrankfurtTime, frankfurt,5.0),
      (ice726CologneTime, essen,0.0)
    ),
    Set(1, 6, 7)
    //Set(xmas, nyd, nye)
  )

  val ice726 = Train(
    InterCityExpress(726),
    schedule726,
  dists
  )

  val ice728MunichTime = Time(8, 50)

  val ice728FrankfurtTime = Time(10, 10)

  val schedule728 = Schedule(
     Vector(
      (ice728MunichTime, munich, 25.0),
      (ice728FrankfurtTime, frankfurt, 0.0)
    ),
  Set(1,2,3,5,6,7),
  Set(nyd)
  )

 val ice728 = Train(
    InterCityExpress(728),
    schedule728,
  dists
 )

  val planner = new JourneyPlanner(Set(ice724, ice726))
  val plannerAllTrains = new JourneyPlanner(Set(ice724, ice726, ice728))

}
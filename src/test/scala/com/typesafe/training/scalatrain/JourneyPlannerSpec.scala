/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }
import com.github.nscala_time.time.Imports._
import org.joda.time.Days
class JourneyPlannerSpec extends WordSpec with Matchers {

  "stations" should {
    "be initialized correctly" in {
      planner.stations shouldEqual Set(munich, nuremberg, frankfurt, cologne, essen)
    }
  }

  "Calling trainsAt" should {
    "return the correct trains" in {
      planner.trainsAt(munich) shouldEqual Set(ice724, ice726)
      planner.trainsAt(cologne) shouldEqual Set(ice724)
    }
  }

  "Calling stopsAt" should {
    "return the correct stops" in {
      planner.stopsAt(munich) shouldEqual Set(ice724MunichTime -> ice724, ice726MunichTime -> ice726)
    }
  }

  "Calling isShortTrip" should {
    "return false for more than one station in between" in {
      planner.isShortTrip(munich, cologne) shouldBe false
      planner.isShortTrip(munich, essen) shouldBe false
    }
    "return true for zero or one stations in between" in {
      planner.isShortTrip(munich, nuremberg) shouldBe true
      planner.isShortTrip(munich, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, essen) shouldBe true
    }
  }


  "Sorting routes from Munich to Frankfurt by price" should {
    "return the ICE724 before the ICE728 express" in {
      val cheapPath = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      val priceyPath = Seq(Hop(munich, frankfurt, ice728))

      val pathSet = Set(cheapPath, priceyPath)
      JourneyPlanner.sortPathsByPrice(pathSet) shouldEqual Seq(cheapPath, priceyPath)
    }
  }

  "Sorting routes from Munich to Frankfurt by time" should {
    "return the the ICE728 express before the ICE724" in {
      val slowPath = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      val fastPath = Seq(Hop(munich, frankfurt, ice728))

      val pathSet = Set(slowPath, fastPath)
      JourneyPlanner.sortPathsByTime(pathSet) shouldEqual Seq(fastPath, slowPath)
    }
  }

  "Listing trains on 2014-12-25" should {
    "only return the ICE724 train" in {
      plannerAllTrains.listTrainsOnDate(xmas) shouldEqual Set(ice724)
    }
  }

  "Listing trains on 2014-12-31" should {
    "only return {ICE724}" in {
      plannerAllTrains.listTrainsOnDate(nyd) shouldEqual Set(ice724)
    }
  }

  "Listing trains on 2015-01-01" should {
    "only return {ICE724}" in {
      plannerAllTrains.listTrainsOnDate(nye) shouldEqual Set(ice724)
    }
  }

  "Planning a trip from Munich to Frankfurt on 2014-12-31" should {
    "only return {ICE724}" in {
      val Route724 = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      plannerAllTrains.planByDate(munich, frankfurt, nyd) shouldEqual Set(Route724)
    }
  }

  "Taking the ICE724 from Munich to Frankfurt today" should {
    "cost 75% x $15 = $11.25" in {
      val Route724 = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      JourneyPlanner.pathPriceOnDate(Route724, DateTime.now, exchangeRate = dummyExchangeRate) shouldEqual Price(11.25)
    }
  }

  def dummyExchangeRate(other: Currency) =  other match {
    case USD => 10.0
    case CAD => 1.0
  }

  "Taking the ICE724 from Munich to Frankfurt next week" should {
    "cost 150% x $15 = $225 USD, 1 CAD = 15 USD" in {
      val Route724 = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      JourneyPlanner.pathPriceOnDate(Route724, DateTime.now.plusDays(7), USD, dummyExchangeRate) shouldEqual Price(225, USD)
    }
  }

  "Taking the ICE724 from Munich to Frankfurt in two weeks" should {
    "cost 100% x $15 = $15" in {
      val Route724 = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      JourneyPlanner.pathPriceOnDate(Route724, DateTime.now.plusDays(15), exchangeRate = dummyExchangeRate) shouldEqual Price(15)
    }
  }

  "planning route" should {
    "return all possible routes that departs after user's departure" in {
      planner.planByTime(munich, frankfurt, Time(10,10)) shouldEqual Set()
      val Route724 = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      planner.planByTime(munich, frankfurt, Time(8,49)) shouldEqual Set(Route724)
      val Route726 = Seq(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice726))
      planner.planByTime(munich, frankfurt, Time(7,49)) shouldEqual Set(Route724, Route726)
    }
  }

  "sink station" should {
    "return station that is no departure station of another train" in {
      planner.sinkStations shouldEqual Set(cologne, essen)
      plannerAllTrains.sinkStations shouldEqual Set(cologne, essen)

    }
  }

}
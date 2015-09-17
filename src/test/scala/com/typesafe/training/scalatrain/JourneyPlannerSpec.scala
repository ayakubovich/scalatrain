/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }
import com.github.nscala_time.time.Imports._
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
      plannerAllTrains.listTrainsOnDate(new DateTime("20141225")) shouldEqual Set(ice724)
    }
  }

  "Listing trains on 2014-12-31" should {
    "only return {ICE724, ICE728}" in {
      plannerAllTrains.listTrainsOnDate(new DateTime("20141231")) shouldEqual Set(ice724, ice728)
    }
  }

  "Listing trains on 2015-01-01" should {
    "only return {ICE724}" in {
      plannerAllTrains.listTrainsOnDate(new DateTime("20141225")) shouldEqual Set(ice724)
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

}
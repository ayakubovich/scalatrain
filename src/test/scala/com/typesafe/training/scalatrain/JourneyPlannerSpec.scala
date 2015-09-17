/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }

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

  "Sorting routes" should {
    "sort paths in the order of total cost" in {
      val slowPath = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      val fastPath = Seq(Hop(munich, frankfurt, ice728))

      val pathSet = Set(slowPath, fastPath)
      JourneyPlanner.sortPathsByTime(pathSet) shouldEqual Seq(fastPath, slowPath)
    }
  }

  "planning route" should {
    "return all possible routes that departs after user's departure" in {
      planner.plan(munich, frankfurt, Time(10,10)) shouldEqual Set()
      val Route724 = Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      planner.plan(munich, frankfurt, Time(8,49)) shouldEqual Set(Route724)
      val Route726 = Seq(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice726))
      planner.plan(munich, frankfurt, Time(7,49)) shouldEqual Set(Route724, Route726)
    }
  }
}

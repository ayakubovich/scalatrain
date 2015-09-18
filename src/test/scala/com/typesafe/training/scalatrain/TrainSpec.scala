/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain
import org.scalatest.{ Matchers, WordSpec }
import TestData._
import java.lang.{ IllegalArgumentException => IAE }
class TrainSpec extends WordSpec with Matchers {

  "Starting a trip from Munich to Frankfurt at 7:49" should {
    "stop in Nurember" in {
      ice724.timeAt(nuremberg) shouldEqual Some(ice724NurembergTime)
    }
    "not stop in Essen" in {
      ice724.timeAt(essen) shouldEqual None
    }
  }

  //////


  "Train ice724" should {
    "stop in Nuremberg" in {
      ice724.timeAt(nuremberg) shouldEqual Some(ice724NurembergTime)
    }
    "stop in Munich" in {
      ice724.timeAt(munich) shouldEqual Some(ice724MunichTime)
    }
    "not stop in Essen" in {
      ice724.timeAt(essen) shouldEqual None
    }
  }

  "Train ice726" should {
    "stop in Munich" in {
      ice726.timeAt(munich) shouldEqual Some(ice726MunichTime)
    }
    "not stop in Cologne" in {
      ice726.timeAt(cologne) shouldEqual None
    }
  }

  "stations" should {
    "be initialized correctly" in {
      ice724.stations shouldEqual Vector(munich, nuremberg, frankfurt, cologne)
    }
  }

  "Calling hops on Train ice724" should {
    "correspond to its schedule: munich > nuremubrg > frankfurt > cologne" in {
      ice724.hops shouldEqual Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724), Hop(frankfurt, cologne,ice724))
    }
  }

  "Generating all hops from Munich to Nuremberg at 8:00" should {
    "return a single hop on train ice724" in {
      ice724.genHop(munich, nuremberg, Time(8)) shouldEqual Seq(Hop(munich, nuremberg, ice724))
    }
  }

  "Taking the 724 from Munich to Frankfurt" should {
    "cost" +
      "" in {
      ice724.genHop(munich, nuremberg, Time(8)) shouldEqual Seq(Hop(munich, nuremberg, ice724))
    }
  }

  "The 724" should {
    "cost" +
      "" in {
      ice724.genHop(munich, nuremberg, Time(8)) shouldEqual Seq(Hop(munich, nuremberg, ice724))
    }
  }

  "check that between on Jan 27, the ICE728" should {
    "travel exactly 2.2 x 28 = 700 km since its last maintenance date (Dec 25 2014)" in {
      ice728.distanceSinceLastMaintained(jan27) should (be < 61.60001 and be > 59.9999)
    }
  }



}

import org.scalatest.{ Matchers, WordSpec }

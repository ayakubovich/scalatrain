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

  "Creating a Train" should {
    "throw an IllegalArgumentException for a schedule with 0 or 1 elements" in {
      an[IAE] should be thrownBy Train(InterCityExpress(724), Vector())
      an[IAE] should be thrownBy Train(InterCityExpress(724), Vector(ice724MunichTime -> munich))
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

}

import org.scalatest.{ Matchers, WordSpec }

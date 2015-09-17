/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import java.util.NoSuchElementException

import org.scalatest.{ Matchers, WordSpec }
import TestData._
import java.lang.{ IllegalArgumentException => IAE }
class HopSpec extends WordSpec with Matchers {

  "Creating a Hop" should {
    "throw an IllegalArgumentException if the endpoints are not on its train's schedule" in {
      an[IAE] should be thrownBy Hop(frankfurt, cologne, ice726)
    }
  }

  "Creating a Hop" should {
    "throw an IllegalArgumentException if the train arrives at its destination before it starts its journey" in {
      an [IAE] should be thrownBy Hop(nuremberg,munich,ice724)
    }
  }

  "A hop from Munich to Nuremberg on the ICE724" should {
    "cost $10" in {
      Hop(munich, nuremberg, ice724).price shouldEqual 10.0
    }
  }

  "Hop should return distance" should {
    "between munich and nuremberg" in {
      Hop(munich, nuremberg, ice724).kms shouldEqual 1.1
    }
    "between nuremberg and munich" in {
      an [NoSuchElementException] should be thrownBy Hop(munich, cologne, ice724).kms
    }
  }

}

/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain
import org.scalatest.{ Matchers, WordSpec }
import TestData._
import java.lang.{ IllegalArgumentException => IAE }
class HopSpec extends WordSpec with Matchers {

  /*"Creating a Hop" should {
    "throw an IllegalArgumentException if not all three arguments are specified" in {
      an[IAE] should be thrownBy Hop(frankfurt, ice726)
      an[IAE] should be thrownBy Hop(frankfurt, cologne)
    }
  }
  */
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

  "Every Hop" should {
    "should arrive at its destination AFTER it leaves its source " in {
      ice724HopMunich2Nuremburg.from shouldEqual Vector(munich, nuremberg, frankfurt, cologne)
    }
  }

}

import org.scalatest.{ Matchers, WordSpec }

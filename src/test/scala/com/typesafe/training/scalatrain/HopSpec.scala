/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain
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

}

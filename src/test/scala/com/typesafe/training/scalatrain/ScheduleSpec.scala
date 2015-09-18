package com.typesafe.training.scalatrain

/**
 * Created by georgeli on 15-09-17.
 */
import org.scalatest.{WordSpec, Matchers}
import TestData._
import java.lang.{ IllegalArgumentException => IAE }
class ScheduleSpec  extends WordSpec with Matchers {


  "Creating a Schedule" should {
    "throw an IllegalArgumentException for a schedule with 0 or 1 elements" in {
      an[IAE] should be thrownBy Schedule(Seq())
      an[IAE] should be thrownBy Schedule(Seq((ice724MunichTime, munich, 0.0)))
    }

    "check the schedule time is sorted by departure time already" in {
      an[IAE] should be thrownBy Schedule(Seq((ice724NurembergTime , munich, 0.0), (ice724MunichTime , frankfurt,0.0)))
    }

    "check the schedule time has no duplicate stations, i.e., cycle" in{
      an[IAE] should be thrownBy Schedule(Seq((ice724MunichTime , munich,0.0), (ice724NurembergTime , munich, 0.0)))
    }

    "check that days of the week values are integers from 1 to 7" in {
      an[IAE] should be thrownBy Schedule(Seq((ice724MunichTime , munich,0.0), (ice724NurembergTime , nuremberg, 0.0)),
        Set(0, 1, 2, 3,-4, 5, 11))
    }

    "check that between Dec 25 2014 and Jan 27 2015, the ICE728" should {
      "travel exactly 28 days" in {
        ice728.schedule.numDaysTravelledBetween2Dates(xmas, jan27) shouldEqual 28
      }
    }

  }
}

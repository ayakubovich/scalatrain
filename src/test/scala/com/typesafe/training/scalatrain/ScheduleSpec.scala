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
  }
}

package com.example

import com.example.model.BountyFootballConstants
import com.example.model.ClubId
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testBountyWheelSlotsCount() {
    assertEquals(20, BountyFootballConstants.WHEEL_SLOTS.size)
  }

  @Test
  fun testClubMultipliers() {
    assertEquals(2, ClubId.PSG.multiplier)
    assertEquals(5, ClubId.BAYERN.multiplier)
    assertEquals(8, ClubId.MAN_UTD.multiplier)
    assertEquals(18, ClubId.AL_QADSIAH.multiplier)
    assertEquals(20, ClubId.AL_HILAL.multiplier)
    assertEquals(30, ClubId.AL_ITTIHAD.multiplier)
    assertEquals(50, ClubId.BAF_PC.multiplier)
    assertEquals(66, ClubId.FALCON_FC.multiplier)
    assertEquals(88, ClubId.RAJA_CA.multiplier)
    assertEquals(100, ClubId.AL_AHLY.multiplier)
  }

  @Test
  fun testChipValues() {
    assertEquals(6, BountyFootballConstants.CHIPS.size)
    assertEquals(10_000L, BountyFootballConstants.CHIPS[0].value)
    assertEquals(100_000L, BountyFootballConstants.CHIPS[1].value)
    assertEquals(500_000L, BountyFootballConstants.CHIPS[2].value)
    assertEquals(1_000_000L, BountyFootballConstants.CHIPS[3].value)
    assertEquals(5_000_000L, BountyFootballConstants.CHIPS[4].value)
    assertEquals(10_000_000L, BountyFootballConstants.CHIPS[5].value)
  }
}


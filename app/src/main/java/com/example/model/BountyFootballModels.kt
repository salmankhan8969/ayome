package com.example.model

import androidx.compose.ui.graphics.Color

enum class ClubId(
    val title: String,
    val shortName: String,
    val multiplier: Int,
    val primaryColor: Color,
    val secondaryColor: Color,
    val badgeSymbol: String
) {
    PSG(
        title = "Paris Saint-Germain",
        shortName = "PSG",
        multiplier = 2,
        primaryColor = Color(0xFF001C3F),
        secondaryColor = Color(0xFFDA291C),
        badgeSymbol = "🗼"
    ),
    BAYERN(
        title = "FC Bayern München",
        shortName = "BAYERN",
        multiplier = 5,
        primaryColor = Color(0xFFDC052D),
        secondaryColor = Color(0xFF0066B2),
        badgeSymbol = "⚽"
    ),
    MAN_UTD(
        title = "Manchester United",
        shortName = "MAN UTD",
        multiplier = 8,
        primaryColor = Color(0xFFDA291C),
        secondaryColor = Color(0xFFFBE122),
        badgeSymbol = "🔱"
    ),
    AL_QADSIAH(
        title = "Al Qadsiah FC",
        shortName = "QADSIAH",
        multiplier = 18,
        primaryColor = Color(0xFFD4AF37),
        secondaryColor = Color(0xFF1B4D3E),
        badgeSymbol = "🦅"
    ),
    AL_HILAL(
        title = "Al Hilal SFC",
        shortName = "AL HILAL",
        multiplier = 20,
        primaryColor = Color(0xFF002D72),
        secondaryColor = Color(0xFFFFFFFF),
        badgeSymbol = "🌙"
    ),
    AL_ITTIHAD(
        title = "Al Ittihad FC",
        shortName = "ITTIHAD",
        multiplier = 30,
        primaryColor = Color(0xFFFFCC00),
        secondaryColor = Color(0xFF111111),
        badgeSymbol = "🐅"
    ),
    BAF_PC(
        title = "BAF PC",
        shortName = "BAF PC",
        multiplier = 50,
        primaryColor = Color(0xFF6A0DAD),
        secondaryColor = Color(0xFFE0AAFF),
        badgeSymbol = "🛡️"
    ),
    FALCON_FC(
        title = "Blue Falcon FC",
        shortName = "FALCON",
        multiplier = 66,
        primaryColor = Color(0xFF0077B6),
        secondaryColor = Color(0xFF90E0EF),
        badgeSymbol = "⚡"
    ),
    RAJA_CA(
        title = "Raja Club Athletic",
        shortName = "RAJA CA",
        multiplier = 88,
        primaryColor = Color(0xFF006837),
        secondaryColor = Color(0xFFFFFFFF),
        badgeSymbol = "🦅"
    ),
    AL_AHLY(
        title = "Al Ahly SC",
        shortName = "AL AHLY",
        multiplier = 100,
        primaryColor = Color(0xFFC8102E),
        secondaryColor = Color(0xFFFFD700),
        badgeSymbol = "👑"
    );

    companion object {
        fun fromId(id: String): ClubId = entries.firstOrNull { it.name == id } ?: PSG
    }
}

/**
 * The 20 perimeter slots on the Bounty Football circular roulette board.
 */
data class WheelSlot(
    val slotIndex: Int,
    val clubId: ClubId,
    val angleDegrees: Float
)

data class BettingChip(
    val value: Long,
    val label: String,
    val mainColor: Color,
    val borderColor: Color,
    val accentColor: Color
)

enum class GamePhase {
    BETTING,   // Countdown timer, player placing bets
    SPINNING,  // Roulette wheel pointer spinning around the 20 slots
    SETTLING   // Highlight winning slot, show win animations & payout, update history
}

data class PlacedBet(
    val clubId: ClubId,
    val amount: Long
)

data class RoundHistoryItem(
    val id: String,
    val clubId: ClubId,
    val isNew: Boolean = false
)

object BountyFootballConstants {
    val CHIPS = listOf(
        BettingChip(10_000L, "10K", Color(0xFF009688), Color(0xFF80CBC4), Color(0xFF004D40)),
        BettingChip(100_000L, "100K", Color(0xFF1976D2), Color(0xFF90CAF9), Color(0xFF0D47A1)),
        BettingChip(500_000L, "500K", Color(0xFF8E24AA), Color(0xFFCE93D8), Color(0xFF4A148C)),
        BettingChip(1_000_000L, "1M", Color(0xFFF57F17), Color(0xFFFFE082), Color(0xFFE65100)),
        BettingChip(5_000_000L, "5M", Color(0xFFD32F2F), Color(0xFFFFCDD2), Color(0xFFB71C1C)),
        BettingChip(10_000_000L, "10M", Color(0xFFE91E63), Color(0xFFF48FB1), Color(0xFF880E4F))
    )

    // 20 wheel slots arranged in order around the circumference
    val WHEEL_SLOTS: List<WheelSlot> = listOf(
        WheelSlot(0, ClubId.PSG, 0f),
        WheelSlot(1, ClubId.BAYERN, 18f),
        WheelSlot(2, ClubId.AL_AHLY, 36f),
        WheelSlot(3, ClubId.BAYERN, 54f),
        WheelSlot(4, ClubId.MAN_UTD, 72f),
        WheelSlot(5, ClubId.PSG, 90f),
        WheelSlot(6, ClubId.AL_HILAL, 108f),
        WheelSlot(7, ClubId.AL_QADSIAH, 126f),
        WheelSlot(8, ClubId.BAF_PC, 144f),
        WheelSlot(9, ClubId.RAJA_CA, 162f),
        WheelSlot(10, ClubId.AL_ITTIHAD, 180f),
        WheelSlot(11, ClubId.PSG, 198f),
        WheelSlot(12, ClubId.BAYERN, 216f),
        WheelSlot(13, ClubId.MAN_UTD, 234f),
        WheelSlot(14, ClubId.FALCON_FC, 252f),
        WheelSlot(15, ClubId.PSG, 270f),
        WheelSlot(16, ClubId.AL_HILAL, 288f),
        WheelSlot(17, ClubId.BAYERN, 306f),
        WheelSlot(18, ClubId.MAN_UTD, 324f),
        WheelSlot(19, ClubId.PSG, 342f)
    )

    // Initial public bets for ambiance matching the screenshot
    val INITIAL_PUBLIC_BETS: Map<ClubId, Long> = mapOf(
        ClubId.PSG to 12_200_000L,
        ClubId.BAYERN to 4_960_000L,
        ClubId.MAN_UTD to 5_020_000L,
        ClubId.AL_QADSIAH to 1_590_000L,
        ClubId.AL_HILAL to 1_590_000L,
        ClubId.AL_ITTIHAD to 1_490_000L,
        ClubId.BAF_PC to 1_200_000L,
        ClubId.FALCON_FC to 1_080_000L,
        ClubId.RAJA_CA to 1_130_000L,
        ClubId.AL_AHLY to 1_490_000L
    )

    fun formatCoins(amount: Long): String {
        return when {
            amount >= 1_000_000_000 -> {
                val b = amount / 1_000_000_000.0
                if (amount % 1_000_000_000L == 0L) "${amount / 1_000_000_000}B"
                else String.format(java.util.Locale.US, "%.1fB", b)
            }
            amount >= 1_000_000 -> {
                val m = amount / 1_000_000.0
                if (amount % 1_000_000L == 0L) "${amount / 1_000_000}M"
                else if (amount % 100_000L == 0L) String.format(java.util.Locale.US, "%.1fM", m)
                else String.format(java.util.Locale.US, "%.2fM", m)
            }
            amount >= 1_000 -> {
                val k = amount / 1_000.0
                if (amount % 1_000L == 0L) "${amount / 1_000}K"
                else String.format(java.util.Locale.US, "%.1fK", k)
            }
            else -> amount.toString()
        }
    }
}

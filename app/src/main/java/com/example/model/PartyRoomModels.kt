package com.example.model

data class RoomSeat(
    val seatNumber: Int,
    val isLocked: Boolean = false,
    val occupantName: String? = null,
    val occupantAvatar: String? = null,
    val isHost: Boolean = false,
    val isSpeaking: Boolean = false,
    val isMuted: Boolean = false
)

data class LuckyBagState(
    val id: String = "68262559",
    val senderName: String = "Yuki",
    val totalGold: Long = 100_000_000L,
    val remainingSeconds: Int = 45,
    val isClaimable: Boolean = true,
    val isClaimed: Boolean = false
)

data class RoomGift(
    val id: String,
    val name: String,
    val icon: String,
    val costGolds: Long
)

enum class ViewMode {
    SPLIT,      // Half Ayome Voice Room, Half Bounty Football (Exact match to screenshot!)
    FULL_GAME,  // Fullscreen Bounty Football
    FULL_ROOM   // Fullscreen Party Voice Room with chat and gifts
}

object AyomeRoomConstants {
    val GIFTS = listOf(
        RoomGift("rose", "Rose", "🌹", 100L),
        RoomGift("lucky_star", "Star", "⭐", 500L),
        RoomGift("heart", "Heart", "💖", 1_000L),
        RoomGift("sports_car", "Supercar", "🏎️", 5_000L),
        RoomGift("crown", "Royal Crown", "👑", 20_000L),
        RoomGift("rocket", "Rocket", "🚀", 50_000L)
    )

    fun createInitialSeats(): List<RoomSeat> = listOf(
        RoomSeat(1, isLocked = false, occupantName = "Yuki", isHost = true, isSpeaking = true),
        RoomSeat(2, isLocked = true),
        RoomSeat(3, isLocked = true),
        RoomSeat(4, isLocked = false, occupantName = "Zs", isHost = false, isSpeaking = false),
        RoomSeat(5, isLocked = true),
        RoomSeat(6, isLocked = false, occupantName = null), // User can sit here!
        RoomSeat(7, isLocked = true),
        RoomSeat(8, isLocked = false, occupantName = null),
        RoomSeat(9, isLocked = false, occupantName = null),
        RoomSeat(10, isLocked = true)
    )
}

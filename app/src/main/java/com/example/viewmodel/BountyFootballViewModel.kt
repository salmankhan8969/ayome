package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AyomeRoomConstants
import com.example.model.BettingChip
import com.example.model.BountyFootballConstants
import com.example.model.ClubId
import com.example.model.GamePhase
import com.example.model.LuckyBagState
import com.example.model.RoomGift
import com.example.model.RoomSeat
import com.example.model.RoundHistoryItem
import com.example.model.ViewMode
import com.example.util.BountySoundManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class BountyGameState(
    val balance: Long = 100_000_000L,
    val selectedChip: BettingChip = BountyFootballConstants.CHIPS[0],
    val currentRoundId: String = "NO.0920000229",
    val phase: GamePhase = GamePhase.BETTING,
    val countdownSeconds: Int = 12,
    val pointerPosition: Float = 0f,
    val winningSlotIndex: Int = 0,
    val winningClub: ClubId? = null,
    val lastWinAmount: Long = 0L,
    val showWinDialog: Boolean = false,
    val showLuckyBagDialog: Boolean = false,
    val showRechargeDialog: Boolean = false,
    val showHelpDialog: Boolean = false,
    val showGiftDialog: Boolean = false,
    val luckyBagClaimAmount: Long = 0L,
    val userBets: Map<ClubId, Long> = emptyMap(),
    val previousBets: Map<ClubId, Long> = emptyMap(),
    val publicBets: Map<ClubId, Long> = BountyFootballConstants.INITIAL_PUBLIC_BETS,
    val history: List<RoundHistoryItem> = emptyList(),
    val isSoundMuted: Boolean = false,
    val viewMode: ViewMode = ViewMode.SPLIT,
    val seats: List<RoomSeat> = AyomeRoomConstants.createInitialSeats(),
    val currentUserSeat: Int? = null,
    val luckyBag: LuckyBagState = LuckyBagState(),
    val statusMessage: String = "Place your bets!"
)

class BountyFootballViewModel(application: Application) : AndroidViewModel(application) {

    private val soundManager = BountySoundManager()
    private val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    private val _uiState = MutableStateFlow(
        BountyGameState(
            history = listOf(
                RoundHistoryItem("1", ClubId.BAYERN, isNew = true),
                RoundHistoryItem("2", ClubId.PSG),
                RoundHistoryItem("3", ClubId.PSG),
                RoundHistoryItem("4", ClubId.PSG),
                RoundHistoryItem("5", ClubId.AL_AHLY),
                RoundHistoryItem("6", ClubId.BAYERN),
                RoundHistoryItem("7", ClubId.PSG),
                RoundHistoryItem("8", ClubId.BAYERN),
                RoundHistoryItem("9", ClubId.BAYERN),
                RoundHistoryItem("10", ClubId.PSG),
                RoundHistoryItem("11", ClubId.PSG),
                RoundHistoryItem("12", ClubId.PSG),
                RoundHistoryItem("13", ClubId.MAN_UTD)
            )
        )
    )
    val uiState: StateFlow<BountyGameState> = _uiState.asStateFlow()

    private var roundSequence = 229L

    init {
        startGameLoop()
    }

    private fun startGameLoop() {
        viewModelScope.launch {
            while (true) {
                // 1. BETTING PHASE (12 seconds)
                _uiState.value = _uiState.value.copy(
                    phase = GamePhase.BETTING,
                    countdownSeconds = 12,
                    winningClub = null,
                    showWinDialog = false,
                    statusMessage = "Place your bets! (12s)"
                )

                for (sec in 12 downTo 1) {
                    _uiState.value = _uiState.value.copy(
                        countdownSeconds = sec,
                        statusMessage = "Betting closes in ${sec}s"
                    )

                    // Simulate slight public bets increase from room participants
                    simulatePublicBets()

                    if (sec <= 3) {
                        soundManager.playCountdownWarning()
                    }
                    delay(1000L)
                }

                // 2. SPINNING PHASE (Wheel accelerates, spins, and decelerates)
                _uiState.value = _uiState.value.copy(
                    phase = GamePhase.SPINNING,
                    countdownSeconds = 0,
                    statusMessage = "Spinning the Wheel..."
                )

                // Pick a target winning slot among the 20 slots
                val targetSlotIndex = Random.nextInt(20)
                val targetClub = BountyFootballConstants.WHEEL_SLOTS[targetSlotIndex].clubId

                // Animate wheel pointer
                val totalLaps = 3 + Random.nextInt(2) // 3 to 4 full revolutions
                val totalSteps = totalLaps * 20 + targetSlotIndex
                var currentPos = _uiState.value.pointerPosition % 20f

                val stepsToRotate = (totalSteps - (currentPos.toInt() % 20) + 20) % 20 + totalLaps * 20
                val spinDurationMs = 5000L
                val startTime = System.currentTimeMillis()

                var lastTickIndex = currentPos.toInt()

                while (System.currentTimeMillis() - startTime < spinDurationMs) {
                    val elapsed = System.currentTimeMillis() - startTime
                    val progress = elapsed.toFloat() / spinDurationMs.toFloat()
                    // Ease out cubic
                    val p = 1f - progress
                    val eased = 1f - (p * p * p)
                    val pos = (currentPos + stepsToRotate * eased)

                    _uiState.value = _uiState.value.copy(
                        pointerPosition = pos
                    )

                    val currentSlot = (pos.toInt() % 20 + 20) % 20
                    if (currentSlot != lastTickIndex) {
                        soundManager.playWheelTick()
                        lastTickIndex = currentSlot
                    }
                    delay(16L) // ~60fps
                }

                // Snap precisely to target slot
                _uiState.value = _uiState.value.copy(
                    pointerPosition = targetSlotIndex.toFloat(),
                    winningSlotIndex = targetSlotIndex,
                    winningClub = targetClub,
                    phase = GamePhase.SETTLING
                )

                vibrateDevice(50L)

                // 3. SETTLING PHASE (Calculate payout & celebration)
                val currentBets = _uiState.value.userBets
                val userBetOnWinner = currentBets[targetClub] ?: 0L
                val winPayout = if (userBetOnWinner > 0) userBetOnWinner * targetClub.multiplier else 0L

                // Update history
                val updatedHistory = listOf(
                    RoundHistoryItem(System.currentTimeMillis().toString(), targetClub, isNew = true)
                ) + _uiState.value.history.take(15).map { it.copy(isNew = false) }

                if (winPayout > 0) {
                    soundManager.playWinSound()
                    _uiState.value = _uiState.value.copy(
                        balance = _uiState.value.balance + winPayout,
                        lastWinAmount = winPayout,
                        showWinDialog = true,
                        history = updatedHistory,
                        statusMessage = "🎉 WON +${BountyFootballConstants.formatCoins(winPayout)} on ${targetClub.shortName}!"
                    )
                } else {
                    soundManager.playWheelTick()
                    _uiState.value = _uiState.value.copy(
                        lastWinAmount = 0L,
                        history = updatedHistory,
                        statusMessage = "${targetClub.title} (X${targetClub.multiplier}) won!"
                    )
                }

                // Wait 4 seconds for players to admire result
                delay(4000L)

                // Prepare next round
                roundSequence++
                val nextRoundId = "NO.0920000$roundSequence"
                _uiState.value = _uiState.value.copy(
                    currentRoundId = nextRoundId,
                    previousBets = _uiState.value.userBets,
                    userBets = emptyMap(),
                    showWinDialog = false
                )
            }
        }
    }

    private fun simulatePublicBets() {
        val clubs = ClubId.entries
        val randomClub = clubs.random()
        val randomAmount = listOf(5000L, 10000L, 25000L, 50000L).random()
        val updated = _uiState.value.publicBets.toMutableMap()
        updated[randomClub] = (updated[randomClub] ?: 0L) + randomAmount
        _uiState.value = _uiState.value.copy(publicBets = updated)
    }

    fun selectChip(chip: BettingChip) {
        _uiState.value = _uiState.value.copy(selectedChip = chip)
        soundManager.playChipSound()
    }

    fun placeBet(clubId: ClubId) {
        if (_uiState.value.phase != GamePhase.BETTING) return
        val chipVal = _uiState.value.selectedChip.value

        if (_uiState.value.balance < chipVal) {
            // Not enough balance, open recharge dialog
            _uiState.value = _uiState.value.copy(showRechargeDialog = true)
            return
        }

        val updatedBets = _uiState.value.userBets.toMutableMap()
        updatedBets[clubId] = (updatedBets[clubId] ?: 0L) + chipVal

        _uiState.value = _uiState.value.copy(
            balance = _uiState.value.balance - chipVal,
            userBets = updatedBets
        )

        soundManager.playChipSound()
        vibrateDevice(25L)
    }

    fun repeatLastBet() {
        if (_uiState.value.phase != GamePhase.BETTING) return
        val prev = _uiState.value.previousBets
        if (prev.isEmpty()) return

        val totalCost = prev.values.sum()
        if (_uiState.value.balance < totalCost) {
            _uiState.value = _uiState.value.copy(showRechargeDialog = true)
            return
        }

        val currentBets = _uiState.value.userBets.toMutableMap()
        prev.forEach { (club, amt) ->
            currentBets[club] = (currentBets[club] ?: 0L) + amt
        }

        _uiState.value = _uiState.value.copy(
            balance = _uiState.value.balance - totalCost,
            userBets = currentBets
        )
        soundManager.playChipSound()
        vibrateDevice(40L)
    }

    fun clearBets() {
        if (_uiState.value.phase != GamePhase.BETTING) return
        val currentBets = _uiState.value.userBets
        val refund = currentBets.values.sum()
        _uiState.value = _uiState.value.copy(
            balance = _uiState.value.balance + refund,
            userBets = emptyMap()
        )
        soundManager.playChipSound()
    }

    fun toggleSound() {
        val nextMuted = !_uiState.value.isSoundMuted
        soundManager.setMuted(nextMuted)
        _uiState.value = _uiState.value.copy(isSoundMuted = nextMuted)
    }

    fun toggleViewMode() {
        val nextMode = when (_uiState.value.viewMode) {
            ViewMode.SPLIT -> ViewMode.FULL_GAME
            ViewMode.FULL_GAME -> ViewMode.FULL_ROOM
            ViewMode.FULL_ROOM -> ViewMode.SPLIT
        }
        _uiState.value = _uiState.value.copy(viewMode = nextMode)
    }

    fun setViewMode(mode: ViewMode) {
        _uiState.value = _uiState.value.copy(viewMode = mode)
    }

    fun claimLuckyBag() {
        val reward = 100_000_000L
        _uiState.value = _uiState.value.copy(
            balance = _uiState.value.balance + reward,
            luckyBagClaimAmount = reward,
            showLuckyBagDialog = true,
            statusMessage = "🎉 Claimed 100M Golds!"
        )
        soundManager.playJackpotSound()
    }

    fun claim100MCoins() {
        val reward = 100_000_000L
        _uiState.value = _uiState.value.copy(
            balance = _uiState.value.balance + reward,
            luckyBagClaimAmount = reward,
            showLuckyBagDialog = true,
            statusMessage = "🎉 Claimed 100M Golds!"
        )
        soundManager.playJackpotSound()
        vibrateDevice(60L)
    }

    fun claimBonus(amount: Long) {
        _uiState.value = _uiState.value.copy(
            balance = _uiState.value.balance + amount,
            showRechargeDialog = false
        )
        soundManager.playJackpotSound()
    }

    fun takeOrLeaveSeat(seatNumber: Int) {
        val currentSeat = _uiState.value.currentUserSeat
        val updatedSeats = _uiState.value.seats.toMutableList()

        if (currentSeat == seatNumber) {
            // Leave seat
            updatedSeats[seatNumber - 1] = updatedSeats[seatNumber - 1].copy(
                occupantName = null,
                isSpeaking = false
            )
            _uiState.value = _uiState.value.copy(
                seats = updatedSeats,
                currentUserSeat = null
            )
        } else if (currentSeat == null && !updatedSeats[seatNumber - 1].isLocked && updatedSeats[seatNumber - 1].occupantName == null) {
            // Take seat
            updatedSeats[seatNumber - 1] = updatedSeats[seatNumber - 1].copy(
                occupantName = "You",
                isSpeaking = true
            )
            _uiState.value = _uiState.value.copy(
                seats = updatedSeats,
                currentUserSeat = seatNumber
            )
            soundManager.playChipSound()
        }
    }

    fun sendGift(gift: RoomGift) {
        if (_uiState.value.balance < gift.costGolds) {
            _uiState.value = _uiState.value.copy(showRechargeDialog = true)
            return
        }
        _uiState.value = _uiState.value.copy(
            balance = _uiState.value.balance - gift.costGolds,
            showGiftDialog = false,
            statusMessage = "Sent ${gift.name} ${gift.icon} to the Room!"
        )
        soundManager.playWinSound()
    }

    fun openHelp(open: Boolean) {
        _uiState.value = _uiState.value.copy(showHelpDialog = open)
    }

    fun openRecharge(open: Boolean) {
        _uiState.value = _uiState.value.copy(showRechargeDialog = open)
    }

    fun openGift(open: Boolean) {
        _uiState.value = _uiState.value.copy(showGiftDialog = open)
    }

    fun dismissWinDialog() {
        _uiState.value = _uiState.value.copy(showWinDialog = false)
    }

    fun dismissLuckyBagDialog() {
        _uiState.value = _uiState.value.copy(showLuckyBagDialog = false)
    }

    private fun vibrateDevice(durationMs: Long) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (_: Exception) {}
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}

package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.BountyFootballConstants
import com.example.model.GamePhase
import com.example.model.ViewMode
import com.example.ui.components.AyomeAudioSeats
import com.example.ui.components.AyomeLuckyBagBanner
import com.example.ui.components.AyomeRoomHeader
import com.example.ui.components.BountyGameBottomBar
import com.example.ui.components.BountyGameTopBar
import com.example.ui.components.BountyRouletteWheel
import com.example.ui.components.FreeRechargeDialog
import com.example.ui.components.HelpRulesDialog
import com.example.ui.components.LuckyBagClaimDialog
import com.example.ui.components.RoomGiftDialog
import com.example.ui.components.WinCelebrationDialog
import com.example.viewmodel.BountyFootballViewModel

@Composable
fun BountyFootballScreen(
    viewModel: BountyFootballViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    // Dialogs
    if (state.showWinDialog && state.winningClub != null) {
        WinCelebrationDialog(
            winningClub = state.winningClub!!,
            winAmount = state.lastWinAmount,
            onDismiss = { viewModel.dismissWinDialog() }
        )
    }

    if (state.showLuckyBagDialog) {
        LuckyBagClaimDialog(
            claimedAmount = state.luckyBagClaimAmount,
            onDismiss = { viewModel.dismissLuckyBagDialog() }
        )
    }

    if (state.showRechargeDialog) {
        FreeRechargeDialog(
            currentBalance = state.balance,
            onClaimBonus = { viewModel.claimBonus(it) },
            onDismiss = { viewModel.openRecharge(false) }
        )
    }

    if (state.showHelpDialog) {
        HelpRulesDialog(
            onDismiss = { viewModel.openHelp(false) }
        )
    }

    if (state.showGiftDialog) {
        RoomGiftDialog(
            userBalance = state.balance,
            onSendGift = { viewModel.sendGift(it) },
            onDismiss = { viewModel.openGift(false) }
        )
    }

    when (state.viewMode) {
        ViewMode.FULL_ROOM -> {
            AyomePartyRoomScreen(
                state = state,
                onToggleViewMode = { viewModel.toggleViewMode() },
                onClaimLuckyBag = { viewModel.claimLuckyBag() },
                onSeatClick = { viewModel.takeOrLeaveSeat(it) },
                onOpenGiftDialog = { viewModel.openGift(true) },
                onOpenGame = { viewModel.setViewMode(ViewMode.SPLIT) },
                modifier = modifier.statusBarsPadding().navigationBarsPadding()
            )
        }
        ViewMode.SPLIT, ViewMode.FULL_GAME -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFF03160A))
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // UPPER SECTION: Ayome Voice Chat Room (Visible in SPLIT mode)
                    if (state.viewMode == ViewMode.SPLIT) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.20f)
                        ) {
                            // Throne background
                            Image(
                                painter = painterResource(id = R.drawable.ayome_throne_bg),
                                contentDescription = "Room Background",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Semi-transparent overlay for contrast
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color(0x99000000), Color(0xCC000000))
                                        )
                                    )
                            )

                            Column(modifier = Modifier.fillMaxSize()) {
                                AyomeRoomHeader(
                                    viewMode = state.viewMode,
                                    onToggleViewMode = { viewModel.toggleViewMode() },
                                    onShare = {},
                                    onClose = { viewModel.setViewMode(ViewMode.FULL_ROOM) }
                                )

                                AyomeLuckyBagBanner(
                                    luckyBag = state.luckyBag,
                                    onClaimClick = { viewModel.claimLuckyBag() }
                                )

                                AyomeAudioSeats(
                                    seats = state.seats,
                                    currentUserSeat = state.currentUserSeat,
                                    onSeatClick = { viewModel.takeOrLeaveSeat(it) }
                                )
                            }
                        }
                    }

                    // LOWER SECTION: Bounty Football Roulette Wheel Game
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(if (state.viewMode == ViewMode.SPLIT) 0.80f else 1.0f)
                    ) {
                        // Green pitch grass background
                        Image(
                            painter = painterResource(id = R.drawable.bounty_pitch_bg),
                            contentDescription = "Pitch Background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Dark green vignette overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(Color(0x22000000), Color(0xAA001B08))
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Top Game Bar (Back, Balance, Help, Sound)
                            BountyGameTopBar(
                                balance = state.balance,
                                isSoundMuted = state.isSoundMuted,
                                onToggleSound = { viewModel.toggleSound() },
                                onOpenHelp = { viewModel.openHelp(true) },
                                onOpenRecharge = { viewModel.openRecharge(true) },
                                onBackClick = { viewModel.setViewMode(ViewMode.FULL_ROOM) },
                                onClaim100M = { viewModel.claim100MCoins() }
                            )

                            // Status Banner (Countdown / Phase info)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xBB00240E))
                                    .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 14.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = state.statusMessage,
                                    color = when (state.phase) {
                                        GamePhase.BETTING -> Color(0xFFFFEB3B)
                                        GamePhase.SPINNING -> Color(0xFF64B5F6)
                                        GamePhase.SETTLING -> Color(0xFFFF80AB)
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            // Center Bounty Roulette Wheel & Betting Cards
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                BountyRouletteWheel(
                                    currentPointerIndex = state.pointerPosition,
                                    winningClub = state.winningClub,
                                    isSettling = (state.phase == GamePhase.SETTLING),
                                    userBets = state.userBets,
                                    publicBets = state.publicBets,
                                    onPlaceBet = { viewModel.placeBet(it) },
                                    modifier = Modifier.fillMaxSize(1.0f)
                                )
                            }

                            // Bottom Game Bar (Round No, Total Invest, History Road, Repeat, Chips)
                            val totalUserInvest = state.userBets.values.sum()
                            val totalRoomInvest = state.publicBets.values.sum() + totalUserInvest

                            BountyGameBottomBar(
                                roundId = state.currentRoundId,
                                totalInvest = totalRoomInvest,
                                myInvest = totalUserInvest,
                                history = state.history,
                                chips = BountyFootballConstants.CHIPS,
                                selectedChip = state.selectedChip,
                                canRepeat = state.previousBets.isNotEmpty() && state.phase == GamePhase.BETTING,
                                onSelectChip = { viewModel.selectChip(it) },
                                onRepeatClick = { viewModel.repeatLastBet() },
                                onClearClick = { viewModel.clearBets() }
                            )
                        }
                    }
                }
            }
        }
    }
}

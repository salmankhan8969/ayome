package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AyomeRoomConstants
import com.example.model.BountyFootballConstants
import com.example.model.ClubId
import com.example.model.RoomGift

@Composable
fun WinCelebrationDialog(
    winningClub: ClubId,
    winAmount: Long,
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "win_dialog_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF160926)),
            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFD700)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .scale(pulseScale)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF330948), Color(0xFF130421))
                        )
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎉 YOU WON! 🎉",
                    color = Color(0xFFFFD700),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                ClubBadgeView(
                    clubId = winningClub,
                    size = 72.dp,
                    showBorderGlow = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = winningClub.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Multiplier: X${winningClub.multiplier}",
                    color = Color(0xFF81C784),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF2E7D32))
                        .border(1.5.dp, Color(0xFFFFD700), RoundedCornerShape(16.dp))
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "+${BountyFootballConstants.formatCoins(winAmount)} Golds",
                        color = Color(0xFFFFEB3B),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB300)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "COLLECT PRIZE",
                        color = Color.Black,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
fun LuckyBagClaimDialog(
    claimedAmount: Long,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF260515)),
            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFF4081)),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF4A0E2E), Color(0xFF1A0510))
                        )
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎁 Super Lucky Bag!",
                    color = Color(0xFFFFEB3B),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Yuki shared gold coins in the room!",
                    color = Color.White,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "💰 +${BountyFootballConstants.formatCoins(claimedAmount)} Golds",
                    color = Color(0xFFFFD54F),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Collect 100M!", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FreeRechargeDialog(
    currentBalance: Long,
    onClaimBonus: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1B29)),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF64B5F6)),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🪙 Gold Coins & Balance",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Current Balance: ${BountyFootballConstants.formatCoins(currentBalance)} Golds",
                    color = Color(0xFFFFD700),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                val bonuses = listOf(
                    Pair("Daily Free Claim", 100_000_000L),
                    Pair("VIP Mega Grant", 100_000_000L),
                    Pair("Super Room Jackpot", 200_000_000L),
                    Pair("Mega Football Pass", 500_000_000L)
                )

                bonuses.forEach { (label, amount) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E2F46))
                            .clickable { onClaimBonus(amount) }
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "+${BountyFootballConstants.formatCoins(amount)} Golds", color = Color(0xFF81C784), fontSize = 12.sp)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF2E7D32))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(text = "CLAIM", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF37474F)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Close", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun RoomGiftDialog(
    userBalance: Long,
    onSendGift: (RoomGift) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F102B)),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFAB47BC)),
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎁 Send Gifts to Party Room",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(180.dp)
                ) {
                    items(AyomeRoomConstants.GIFTS) { gift ->
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF331A45))
                                .border(1.dp, Color(0xFF8E24AA), RoundedCornerShape(12.dp))
                                .clickable { onSendGift(gift) }
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = gift.icon, fontSize = 26.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = gift.name, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = "${gift.costGolds} 🪙",
                                color = Color(0xFFFFD54F),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A148C))
                ) {
                    Text(text = "Done", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun HelpRulesDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0D2818)),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "⚽ Bounty Football Rules",
                    color = Color(0xFFFFD700),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "1. Gameplay:\n" +
                            "Select chips (200, 1K, 20K, 100K, 150K) and place bets on football clubs during the 12-second countdown.\n\n" +
                            "2. Multipliers & Clubs:\n" +
                            "• PSG: X2\n" +
                            "• FC Bayern: X5\n" +
                            "• Manchester United: X8\n" +
                            "• Al Qadsiah: X18\n" +
                            "• Al Hilal: X20\n" +
                            "• Al Ittihad: X30\n" +
                            "• BAF PC: X50\n" +
                            "• Blue Falcon: X66\n" +
                            "• Raja CA: X88\n" +
                            "• Al Ahly SC: X100\n\n" +
                            "3. Spinning Wheel:\n" +
                            "The light pointer spins along the 20 perimeter slots and lands on the winner. If you bet on that club, your bet is multiplied and credited instantly!\n\n" +
                            "4. Ayome Voice Room:\n" +
                            "Tap lucky bags to grab free golds, interact on the audio seats, and cheer with friends!",
                    color = Color.White,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text(text = "Got it!", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

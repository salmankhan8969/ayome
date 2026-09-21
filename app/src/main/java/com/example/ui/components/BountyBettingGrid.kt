package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BountyFootballConstants
import com.example.model.ClubId

@Composable
fun BountyBettingGrid(
    userBets: Map<ClubId, Long>,
    publicBets: Map<ClubId, Long>,
    winningClub: ClubId?,
    isSettling: Boolean,
    onPlaceBet: (ClubId) -> Unit,
    modifier: Modifier = Modifier
) {
    // 5 rows of 2 clubs each
    val rows = listOf(
        Pair(ClubId.PSG, ClubId.BAYERN),
        Pair(ClubId.MAN_UTD, ClubId.AL_QADSIAH),
        Pair(ClubId.FALCON_FC, ClubId.BAF_PC),
        Pair(ClubId.AL_AHLY, ClubId.RAJA_CA),
        Pair(ClubId.AL_ITTIHAD, ClubId.AL_HILAL)
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xD90A3818)) // Rich football green pitch felt
            .border(1.5.dp, Color(0x664E9B58), RoundedCornerShape(12.dp))
            .padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        rows.forEachIndexed { rowIndex, (leftClub, rightClub) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BetTile(
                    club = leftClub,
                    userBet = userBets[leftClub] ?: 0L,
                    publicBet = publicBets[leftClub] ?: 0L,
                    isWinner = isSettling && winningClub == leftClub,
                    onClick = { onPlaceBet(leftClub) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )

                // Vertical divider line
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxSize()
                        .background(Color(0x444E9B58))
                )

                BetTile(
                    club = rightClub,
                    userBet = userBets[rightClub] ?: 0L,
                    publicBet = publicBets[rightClub] ?: 0L,
                    isWinner = isSettling && winningClub == rightClub,
                    onClick = { onPlaceBet(rightClub) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )
            }

            if (rowIndex < rows.size - 1) {
                // Horizontal divider line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0x444E9B58))
                )
            }
        }
    }
}

@Composable
private fun BetTile(
    club: ClubId,
    userBet: Long,
    publicBet: Long,
    isWinner: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "winner_flash")
    val flashAlpha by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 0.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(300),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flash_alpha"
    )

    val backgroundColor = when {
        isWinner -> Color(0xFFFFD700).copy(alpha = flashAlpha)
        userBet > 0 -> Color(0x44FFD700)
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Club Crest
            ClubBadgeView(
                clubId = club,
                size = 32.dp,
                showBorderGlow = isWinner
            )

            Spacer(modifier = Modifier.width(2.dp))

            // Multiplier & Bets Column
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                // Multiplier label (e.g. X2, X5, X100)
                Text(
                    text = "X${club.multiplier}",
                    color = if (isWinner) Color(0xFFFFD700) else Color(0xFFC0E0C5),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )

                // Total pool
                val totalAmount = publicBet + userBet
                Text(
                    text = BountyFootballConstants.formatCoins(totalAmount),
                    color = Color(0xFFE8F5E9),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // User placed bet chip badge at bottom-center / top-right if user has bet
        if (userBet > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xDDFF9800))
                    .border(1.dp, Color(0xFFFFE082), RoundedCornerShape(6.dp))
                    .padding(horizontal = 3.dp, vertical = 1.dp)
            ) {
                Text(
                    text = BountyFootballConstants.formatCoins(userBet),
                    color = Color.Black,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BettingChip
import com.example.model.BountyFootballConstants
import com.example.model.RoundHistoryItem

@Composable
fun BountyGameBottomBar(
    roundId: String,
    totalInvest: Long,
    myInvest: Long,
    history: List<RoundHistoryItem>,
    chips: List<BettingChip>,
    selectedChip: BettingChip,
    canRepeat: Boolean,
    onSelectChip: (BettingChip) -> Unit,
    onRepeatClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        // Round info & Investment summary
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = roundId,
                color = Color(0xFFC0E0C5),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                Text(
                    text = "Total: ",
                    color = Color(0xFFA5D6A7),
                    fontSize = 11.sp
                )
                Text(
                    text = BountyFootballConstants.formatCoins(totalInvest),
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "My: ",
                    color = Color(0xFFA5D6A7),
                    fontSize = 11.sp
                )
                Text(
                    text = BountyFootballConstants.formatCoins(myInvest),
                    color = if (myInvest > 0) Color(0xFFFFD54F) else Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // History tape strip (golden rounded ribbon)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xCC0D2818))
                .border(1.5.dp, Color(0xFFC9A227), RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                history.forEachIndexed { index, item ->
                    Box(contentAlignment = Alignment.TopCenter) {
                        ClubBadgeView(
                            clubId = item.clubId,
                            size = 22.dp,
                            showBorderGlow = (index == 0)
                        )

                        // "NEW" tag on first item
                        if (index == 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFFE53935))
                                    .padding(horizontal = 2.dp, vertical = 0.dp)
                            ) {
                                Text(
                                    text = "new",
                                    color = Color.White,
                                    fontSize = 7.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Actions (Repeat, Clear) and Chip Selector Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Chips (200, 1K, 20K, 100K, 150K)
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                chips.forEach { chip ->
                    BettingChipView(
                        chip = chip,
                        isSelected = chip == selectedChip,
                        onClick = { onSelectChip(chip) },
                        size = 50.dp
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Repeat & Clear buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                // REPEAT circular button
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = if (canRepeat) {
                                    listOf(Color(0xFFFFDF7A), Color(0xFFC79500), Color(0xFF8B6508))
                                } else {
                                    listOf(Color(0xFF757575), Color(0xFF424242))
                                }
                            )
                        )
                        .border(
                            1.5.dp,
                            if (canRepeat) Color(0xFFFFE082) else Color(0xFF9E9E9E),
                            CircleShape
                        )
                        .clickable(enabled = canRepeat) { onRepeatClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "REPEAT",
                        color = if (canRepeat) Color(0xFF1B263B) else Color(0xFFBDBDBD),
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // CLEAR button
                if (myInvest > 0) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xAA330000))
                            .border(1.dp, Color(0xFFEF5350), RoundedCornerShape(6.dp))
                            .clickable { onClearClick() }
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "CLEAR",
                            color = Color(0xFFFFCDD2),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

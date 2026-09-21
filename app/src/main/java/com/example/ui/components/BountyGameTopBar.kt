package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
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
import com.example.model.BountyFootballConstants

@Composable
fun BountyGameTopBar(
    balance: Long,
    isSoundMuted: Boolean,
    onToggleSound: () -> Unit,
    onOpenHelp: () -> Unit,
    onOpenRecharge: () -> Unit,
    onBackClick: () -> Unit,
    onClaim100M: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Action Buttons: Exit & Room Players
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Green back button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
                            )
                        )
                        .border(1.5.dp, Color(0xFF81C784), CircleShape)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Exit",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                // Green group button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
                            )
                        )
                        .border(1.5.dp, Color(0xFF81C784), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = "Players",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            // Center: Balance pill and +100M Claim Button
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32), Color(0xFF388E3C))
                            )
                        )
                        .border(1.5.dp, Color(0xFF81C784), RoundedCornerShape(18.dp))
                        .clickable { onOpenRecharge() }
                        .padding(horizontal = 9.dp, vertical = 3.5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🪙",
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = BountyFootballConstants.formatCoins(balance),
                            color = Color(0xFFFFD54F),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = ">>",
                            color = Color(0xFF81C784),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                // +100M Instant Claim Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFF6F00), Color(0xFFFFB300))
                            )
                        )
                        .border(1.5.dp, Color(0xFFFFF9C4), RoundedCornerShape(14.dp))
                        .clickable { onClaim100M() }
                        .padding(horizontal = 7.dp, vertical = 3.5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🎁", fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "+100M",
                            color = Color(0xFF2B1700),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            // Right Actions: Help, Sound & Ping
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Help button
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32))
                        .border(1.5.dp, Color(0xFF81C784), CircleShape)
                        .clickable { onOpenHelp() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = "Help",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Sound toggle button
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32))
                        .border(1.5.dp, Color(0xFF81C784), CircleShape)
                        .clickable { onToggleSound() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSoundMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
                        contentDescription = "Sound",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Sub-row with ping indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, end = 4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = "Ping",
                tint = Color(0xFFFFD54F),
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "387ms",
                color = Color(0xFFFFD54F),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

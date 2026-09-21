package com.example.ui.components

import com.example.model.BountyFootballConstants
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.LuckyBagState
import com.example.model.RoomSeat
import com.example.model.ViewMode

@Composable
fun AyomeRoomHeader(
    roomTitle: String = "Yuki",
    roomId: String = "879365",
    viewMode: ViewMode,
    onToggleViewMode: () -> Unit,
    onShare: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left: Host Profile Badge
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0x88000000))
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color(0xFFFFD700), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ayome_throne_bg),
                    contentDescription = "Host Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "💎 Yuki",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    // Level badge 14
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFFF9800), Color(0xFFE91E63))
                                )
                            )
                            .padding(horizontal = 3.dp, vertical = 0.5.dp)
                    ) {
                        Text(
                            text = "Lv.14",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = "Room:$roomId",
                    color = Color(0xCCFFFFFF),
                    fontSize = 10.sp
                )
            }
        }

        // Center: Trophy & Viewers
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Trophy
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x66000000))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Trophy",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "0 >",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Viewers
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x66000000))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Viewers",
                    tint = Color(0xFF81C784),
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "128",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Right Action icons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Share
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0x66000000))
                    .clickable { onShare() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )
            }

            // Toggle Fullscreen / Split
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0x66000000))
                    .clickable { onToggleViewMode() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (viewMode == ViewMode.SPLIT) Icons.Default.Fullscreen else Icons.Default.FullscreenExit,
                    contentDescription = "View Mode",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }

            // Close
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0x66000000))
                    .clickable { onClose() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun AyomeLuckyBagBanner(
    luckyBag: LuckyBagState,
    onClaimClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bag_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bag_scale"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .scale(pulseScale)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFE91E63),
                        Color(0xFFFF4081),
                        Color(0xFFFFB300),
                        Color(0xFFFFD700)
                    )
                )
            )
            .border(1.5.dp, Color(0xFFFFEB3B), RoundedCornerShape(24.dp))
            .clickable { onClaimClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Sender avatar
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👑", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Center text: Super luckybag banner
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✨ Super luckybag ✨",
                    color = Color.Yellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "${luckyBag.id} Sent a Super luckybag",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Right: Golds amount badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFB71C1C))
                    .border(1.dp, Color(0xFFFFD700), RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = BountyFootballConstants.formatCoins(luckyBag.totalGold),
                        color = Color(0xFFFFD700),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Golds",
                        color = Color.White,
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AyomeAudioSeats(
    seats: List<RoomSeat>,
    currentUserSeat: Int?,
    onSeatClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // Row 1: Seats 1..5
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            seats.take(5).forEach { seat ->
                AudioSeatItem(
                    seat = seat,
                    isCurrentUser = (currentUserSeat == seat.seatNumber),
                    onClick = { onSeatClick(seat.seatNumber) }
                )
            }
        }

        // Row 2: Seats 6..10
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            seats.drop(5).take(5).forEach { seat ->
                AudioSeatItem(
                    seat = seat,
                    isCurrentUser = (currentUserSeat == seat.seatNumber),
                    onClick = { onSeatClick(seat.seatNumber) }
                )
            }
        }
    }
}

@Composable
private fun AudioSeatItem(
    seat: RoomSeat,
    isCurrentUser: Boolean,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "seat_ripple")
    val rippleScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ripple_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(1.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x995C3802), Color(0xCC261501))
                    )
                )
                .border(
                    width = 1.dp,
                    color = if (seat.isSpeaking) Color(0xFFFFD700) else Color(0x88D4AF37),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Speaking ripple effect
            if (seat.isSpeaking) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(rippleScale)
                        .border(1.dp, Color(0x66FFD700), CircleShape)
                )
            }

            when {
                seat.isHost -> {
                    Text(text = "👑", fontSize = 15.sp)
                }
                isCurrentUser -> {
                    Text(text = "🧑", fontSize = 15.sp)
                }
                seat.occupantName != null -> {
                    Text(text = "🌟", fontSize = 15.sp)
                }
                seat.isLocked -> {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(14.dp)
                    )
                }
                else -> {
                    // Open Armchair Golden Seat
                    Text(text = "💺", fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(1.dp))

        // Seat label / number
        Text(
            text = when {
                seat.isHost -> "Host"
                isCurrentUser -> "Me"
                seat.occupantName != null -> seat.occupantName
                else -> "${seat.seatNumber}"
            },
            color = Color(0xFFFFE082),
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

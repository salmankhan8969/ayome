package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.components.AyomeAudioSeats
import com.example.ui.components.AyomeLuckyBagBanner
import com.example.ui.components.AyomeRoomHeader
import com.example.viewmodel.BountyGameState

data class RoomChatMessage(
    val sender: String,
    val text: String,
    val tagColor: Color = Color(0xFFFFD54F)
)

@Composable
fun AyomePartyRoomScreen(
    state: BountyGameState,
    onToggleViewMode: () -> Unit,
    onClaimLuckyBag: () -> Unit,
    onSeatClick: (Int) -> Unit,
    onOpenGiftDialog: () -> Unit,
    onOpenGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    val messages = remember {
        mutableStateListOf(
            RoomChatMessage("System", "Welcome to Yuki's Luxury Lounge! 🌟", Color(0xFF64B5F6)),
            RoomChatMessage("Yuki (Host)", "Welcome everyone! Enjoy Bounty Football round 229!", Color(0xFFFF80AB)),
            RoomChatMessage("Zs", "Who's betting on Al Ahly X100 tonight? 🔥", Color(0xFFFFD54F)),
            RoomChatMessage("Sultan", "Bayern Munich has been hot today! ⚽", Color(0xFF81C784)),
            RoomChatMessage("System", "Player 68262559 won 149.2K Golds on Al Ahly!", Color(0xFFFFD700))
        )
    }

    var messageText by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF13041F))
    ) {
        // Throne Room Background
        Image(
            painter = painterResource(id = R.drawable.ayome_throne_bg),
            contentDescription = "Room Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0x99000000), Color(0xDD000000))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp)
        ) {
            // Header
            AyomeRoomHeader(
                viewMode = state.viewMode,
                onToggleViewMode = onToggleViewMode,
                onShare = {},
                onClose = onToggleViewMode
            )

            // Lucky Bag Banner
            AyomeLuckyBagBanner(
                luckyBag = state.luckyBag,
                onClaimClick = onClaimLuckyBag
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Audio Seats
            AyomeAudioSeats(
                seats = state.seats,
                currentUserSeat = state.currentUserSeat,
                onSeatClick = onSeatClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Floating Bounty Football Shortcut Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32), Color(0xFF1B5E20))
                        )
                    )
                    .border(1.5.dp, Color(0xFFFFD700), RoundedCornerShape(16.dp))
                    .clickable { onOpenGame() }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⚽", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Bounty Football Live",
                                color = Color(0xFFFFD700),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = state.statusMessage,
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFFFFD700))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "PLAY NOW",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Live Room Chat Feed
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(messages) { msg ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x77000000))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${msg.sender}: ",
                            color = msg.tagColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = msg.text,
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Bottom Action & Chat Input Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Say something...", color = Color.Gray, fontSize = 12.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color(0x66FFFFFF),
                        focusedContainerColor = Color(0x88000000),
                        unfocusedContainerColor = Color(0x88000000)
                    ),
                    shape = RoundedCornerShape(22.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(6.dp))

                // Send message button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF6A1B9A))
                        .clickable {
                            if (messageText.isNotBlank()) {
                                messages.add(RoomChatMessage("You", messageText, Color(0xFF64B5F6)))
                                messageText = ""
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Gift Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFFFF4081), Color(0xFFC2185B))
                            )
                        )
                        .border(1.dp, Color(0xFFFFD700), CircleShape)
                        .clickable { onOpenGiftDialog() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CardGiftcard,
                        contentDescription = "Gift",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

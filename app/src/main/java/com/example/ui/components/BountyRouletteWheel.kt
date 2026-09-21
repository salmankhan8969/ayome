package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.model.BountyFootballConstants
import com.example.model.ClubId
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BountyRouletteWheel(
    currentPointerIndex: Float, // 0f..19f (continuous for smooth spin)
    winningClub: ClubId?,
    isSettling: Boolean,
    userBets: Map<ClubId, Long>,
    publicBets: Map<ClubId, Long>,
    onPlaceBet: (ClubId) -> Unit,
    modifier: Modifier = Modifier
) {
    // Running lights pulse
    val infiniteTransition = rememberInfiniteTransition(label = "running_lights")
    val lightOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "light_offset"
    )

    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF0F5128), Color(0xFF072914))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val wheelSize = minOf(maxWidth, maxHeight)
        val radiusPx = wheelSize / 2f
        val badgeSize = (wheelSize * 0.136f).coerceIn(32.dp, 48.dp)

        // Draw outer golden ring and LED bulbs
        Canvas(modifier = Modifier.size(wheelSize)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val outerRadius = size.width / 2f - 2.dp.toPx()

            // Outer golden beveled rim
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFFDF7A), Color(0xFFB8860B), Color(0xFF8B6508)),
                    center = center,
                    radius = outerRadius
                ),
                radius = outerRadius,
                style = Stroke(width = 6.dp.toPx())
            )

            // Inner gold rim line
            drawCircle(
                color = Color(0xFFFFD700),
                radius = outerRadius - 4.dp.toPx(),
                style = Stroke(width = 1.2.dp.toPx())
            )

            // Circular LED light bulbs (40 bulbs around the perimeter)
            val numBulbs = 40
            for (i in 0 until numBulbs) {
                val bulbAngle = (i * 360f / numBulbs) + (lightOffset * 360f / numBulbs)
                val bulbAngleRad = Math.toRadians(bulbAngle.toDouble())
                val bulbRadius = outerRadius - 2.dp.toPx()
                val bulbCenter = Offset(
                    center.x + (bulbRadius * cos(bulbAngleRad)).toFloat(),
                    center.y + (bulbRadius * sin(bulbAngleRad)).toFloat()
                )

                val isLit = (i % 2 == 0)
                drawCircle(
                    color = if (isLit) Color(0xFFFFF7C2) else Color(0xFFFFB300),
                    radius = if (isLit) 3.dp.toPx() else 2.dp.toPx(),
                    center = bulbCenter
                )
            }

            // Green pitch track area border
            drawCircle(
                color = Color(0x884E9B58),
                radius = outerRadius - 24.dp.toPx(),
                style = Stroke(width = 1.2.dp.toPx())
            )
        }

        // Place the 20 club badges along the track
        val slots = BountyFootballConstants.WHEEL_SLOTS
        val trackRadius = (wheelSize / 2f) - (badgeSize / 2f) - 5.dp
        val activeSlotIndex = (currentPointerIndex.toInt() % 20 + 20) % 20

        slots.forEachIndexed { index, slot ->
            // Angle starting from top (-90 degrees)
            val angleDeg = (index * (360f / 20f)) - 90f
            val angleRad = Math.toRadians(angleDeg.toDouble())
            val offsetX = (trackRadius.value * cos(angleRad)).dp
            val offsetY = (trackRadius.value * sin(angleRad)).dp

            val isPointerOnThisSlot = (index == activeSlotIndex)
            val isWinningSlot = isSettling && (slot.clubId == winningClub) && isPointerOnThisSlot

            Box(
                modifier = Modifier
                    .offset(x = offsetX, y = offsetY),
                contentAlignment = Alignment.Center
            ) {
                ClubBadgeView(
                    clubId = slot.clubId,
                    size = badgeSize,
                    showBorderGlow = isPointerOnThisSlot || isWinningSlot,
                    glowColor = if (isWinningSlot) Color(0xFFFF0055) else Color(0xFFFFD700)
                )
            }
        }

        // Center Betting Grid
        val centerGridSize = wheelSize * 0.67f
        BountyBettingGrid(
            userBets = userBets,
            publicBets = publicBets,
            winningClub = winningClub,
            isSettling = isSettling,
            onPlaceBet = onPlaceBet,
            modifier = Modifier
                .size(centerGridSize)
                .padding(1.dp)
        )
    }
}

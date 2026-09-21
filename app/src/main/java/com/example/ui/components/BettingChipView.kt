package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BettingChip

@Composable
fun BettingChipView(
    chip: BettingChip,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.10f else 1.0f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "chip_scale"
    )

    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isSelected) {
                        listOf(Color(0xFFFFE082), Color(0xFF2A1C04))
                    } else {
                        listOf(Color(0xFF1E2836), Color(0xFF0D141C))
                    }
                )
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFFFFD700) else Color(0x55E2C974),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        // Poker chip disc
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(elevation = 6.dp, shape = CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(this.size.width / 2f, this.size.height / 2f)
                val radius = this.size.width / 2f

                // Outer notched casino chip base
                drawCircle(
                    color = chip.mainColor,
                    radius = radius
                )

                // White dash notches around chip rim
                val numNotches = 8
                val notchAngle = 360f / numNotches
                for (i in 0 until numNotches) {
                    val angleRad = Math.toRadians((i * notchAngle).toDouble())
                    val notchCenter = Offset(
                        center.x + (radius * 0.85f * Math.cos(angleRad)).toFloat(),
                        center.y + (radius * 0.85f * Math.sin(angleRad)).toFloat()
                    )
                    drawCircle(
                        color = chip.borderColor,
                        radius = radius * 0.12f,
                        center = notchCenter
                    )
                }

                // Inner ring
                drawCircle(
                    color = chip.accentColor,
                    radius = radius * 0.72f
                )

                // Inner golden border ring
                drawCircle(
                    color = Color(0xFFFFD700),
                    radius = radius * 0.70f,
                    style = Stroke(width = 1.5f)
                )

                // Shiny top-left glare
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x66FFFFFF), Color(0x00FFFFFF)),
                        center = Offset(center.x - radius * 0.25f, center.y - radius * 0.25f),
                        radius = radius * 0.5f
                    ),
                    radius = radius * 0.65f
                )
            }

            // Chip value text
            Text(
                text = chip.label,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = if (chip.label.length >= 4) 11.sp else 13.sp
            )
        }
    }
}

package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClubId

@Composable
fun ClubBadgeView(
    clubId: ClubId,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    showBorderGlow: Boolean = false,
    glowColor: Color = Color(0xFFFFD700)
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFF101820))
            .then(
                if (showBorderGlow) {
                    Modifier.border(2.dp, glowColor, CircleShape)
                } else {
                    Modifier.border(1.dp, Color(0xFFE2C974), CircleShape)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val radius = this.size.width / 2f

            when (clubId) {
                ClubId.PSG -> {
                    // Paris Saint-Germain: Deep Navy background, Red center stripe, Gold accent ring
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF0A2342), Color(0xFF001226)),
                            center = center,
                            radius = radius
                        )
                    )
                    // Red center ring
                    drawCircle(
                        color = Color(0xFFDA291C),
                        radius = radius * 0.72f,
                        style = Stroke(width = radius * 0.18f)
                    )
                    drawCircle(
                        color = Color(0xFFFFFFFF),
                        radius = radius * 0.48f
                    )
                    drawCircle(
                        color = Color(0xFF0A2342),
                        radius = radius * 0.42f
                    )
                }
                ClubId.BAYERN -> {
                    // Bayern Munich: Red ring, Blue & White center diamonds
                    drawCircle(color = Color(0xFFDC052D))
                    drawCircle(
                        color = Color(0xFFFFFFFF),
                        radius = radius * 0.75f,
                        style = Stroke(width = radius * 0.15f)
                    )
                    drawCircle(
                        color = Color(0xFF0066B2),
                        radius = radius * 0.6f
                    )
                    // Bavarian diamond accent
                    val path = Path().apply {
                        moveTo(center.x, center.y - radius * 0.4f)
                        lineTo(center.x + radius * 0.4f, center.y)
                        lineTo(center.x, center.y + radius * 0.4f)
                        lineTo(center.x - radius * 0.4f, center.y)
                        close()
                    }
                    drawPath(path, color = Color.White)
                }
                ClubId.MAN_UTD -> {
                    // Manchester United: Red and Yellow with Devil / Trident shield
                    drawCircle(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFE51A24), Color(0xFF9E0B13))
                        )
                    )
                    drawCircle(
                        color = Color(0xFFFFD100),
                        radius = radius * 0.75f,
                        style = Stroke(width = radius * 0.16f)
                    )
                }
                ClubId.AL_QADSIAH -> {
                    // Al Qadsiah: Gold & Green Falcon Shield
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFF9C80E), Color(0xFFC79500))
                        )
                    )
                    drawCircle(
                        color = Color(0xFF1B4D3E),
                        radius = radius * 0.65f
                    )
                }
                ClubId.AL_HILAL -> {
                    // Al Hilal: Royal Blue with White Crescent
                    drawCircle(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF003087), Color(0xFF001A4E))
                        )
                    )
                    drawCircle(
                        color = Color(0xFFFFFFFF),
                        radius = radius * 0.55f,
                        center = Offset(center.x + radius * 0.1f, center.y)
                    )
                    drawCircle(
                        color = Color(0xFF003087),
                        radius = radius * 0.50f,
                        center = Offset(center.x + radius * 0.22f, center.y)
                    )
                }
                ClubId.AL_ITTIHAD -> {
                    // Al Ittihad: Yellow & Black Tiger stripes
                    drawCircle(color = Color(0xFFFFCC00))
                    val stripeWidth = radius * 0.25f
                    drawRect(
                        color = Color(0xFF1A1A1A),
                        topLeft = Offset(center.x - stripeWidth * 1.5f, 0f),
                        size = androidx.compose.ui.geometry.Size(stripeWidth, this.size.height)
                    )
                    drawRect(
                        color = Color(0xFF1A1A1A),
                        topLeft = Offset(center.x + stripeWidth * 0.5f, 0f),
                        size = androidx.compose.ui.geometry.Size(stripeWidth, this.size.height)
                    )
                    drawCircle(
                        color = Color(0xFFFFCC00),
                        radius = radius * 0.45f
                    )
                }
                ClubId.BAF_PC -> {
                    // BAF PC: Purple & Silver Shield
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF7B2CBF), Color(0xFF3C096C))
                        )
                    )
                    drawCircle(
                        color = Color(0xFFE0AAFF),
                        radius = radius * 0.70f,
                        style = Stroke(width = radius * 0.14f)
                    )
                }
                ClubId.FALCON_FC -> {
                    // Blue Falcon FC: Cyan & Azure Wings
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF00B4D8), Color(0xFF0077B6))
                        )
                    )
                    drawCircle(
                        color = Color(0xFFCAF0F8),
                        radius = radius * 0.65f,
                        style = Stroke(width = radius * 0.12f)
                    )
                }
                ClubId.RAJA_CA -> {
                    // Raja Club Athletic: Rich Emerald Green with White Eagle
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF008744), Color(0xFF004D25))
                        )
                    )
                    drawCircle(
                        color = Color(0xFFFFFFFF),
                        radius = radius * 0.72f,
                        style = Stroke(width = radius * 0.12f)
                    )
                }
                ClubId.AL_AHLY -> {
                    // Al Ahly: Red "Club of the Century" with Golden Stars
                    drawCircle(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFD90429), Color(0xFF8D0801))
                        )
                    )
                    drawCircle(
                        color = Color(0xFFFFD700),
                        radius = radius * 0.75f,
                        style = Stroke(width = radius * 0.15f)
                    )
                }
            }

            // Outer golden ring
            drawCircle(
                color = Color(0xFFD4AF37),
                radius = radius - 1f,
                style = Stroke(width = 2f)
            )
        }

        // Inner symbolic emoji / letter representation
        val fontSize = (size.value * 0.42f).sp
        Text(
            text = clubId.badgeSymbol,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

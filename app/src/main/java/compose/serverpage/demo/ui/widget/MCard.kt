package compose.serverpage.demo.ui.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import compose.serverpage.demo.R

@Composable
fun SmallColorIconCardWithBorder(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    iconSize: Dp = 18.dp,
    color: Color,
    shape: Shape = RoundedCornerShape(12.dp),
    horizontalSpaceBy: Dp = 8.dp,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = color.copy(alpha = 0.1f),
            contentColor = color
        ),
        shape = shape,
        border = BorderStroke(width = borderWidth, color.copy(alpha = 0.2f)),
        modifier = Modifier
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(horizontalSpaceBy),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = imageVector,
                contentDescription = null
            )
            content()
        }
    }
}

@Composable
fun GlowingGradientBackground(
    modifier: Modifier = Modifier
) {
    val alphaAnim by rememberInfiniteTransition(label = "glow")
        .animateFloat(
            initialValue = 0.08f,
            targetValue = 0.25f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alphaAnim"
        )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, top = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(y = (-150).dp, x = (-80).dp)
                .align(Alignment.TopStart)
                .padding(top = 56.dp, start = 16.dp)
                .graphicsLayer {
                    alpha = alphaAnim
                }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colorResource(R.color.md2_indigo_500).copy(alpha = 1f),
                            Color.Transparent
                        ),
                        radius = 500f,
                        center = Offset.Unspecified
                    )
                )
                .blur(300.dp)
        )
    }
}
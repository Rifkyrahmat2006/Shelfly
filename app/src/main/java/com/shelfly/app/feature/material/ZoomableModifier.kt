package com.shelfly.app.feature.material

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

// PIC: Person C — Pinch-zoom + pan reusable, dipakai PDF viewer & Image viewer
// (user request: PDF bisa di-zoom, gambar bisa dilihat langsung in-app).
// Native gesture detection (foundation.gestures), tanpa library zoom eksternal.
private const val MIN_SCALE = 1f
private const val MAX_SCALE = 5f

@Composable
fun Modifier.zoomable(): Modifier {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    return this
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scale = (scale * zoom).coerceIn(MIN_SCALE, MAX_SCALE)
                offsetX += pan.x
                offsetY += pan.y
                if (scale == MIN_SCALE) {
                    offsetX = 0f
                    offsetY = 0f
                }
            }
        }
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            translationX = offsetX,
            translationY = offsetY,
        )
}

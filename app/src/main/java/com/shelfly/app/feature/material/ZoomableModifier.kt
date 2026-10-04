package com.shelfly.app.feature.material

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged

private const val MIN_SCALE = 1f
private const val MAX_SCALE = 5f

@Composable
fun Modifier.zoomable(): Modifier {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    return this
        .pointerInput(Unit) {
            awaitEachGesture {
                do {
                    val event = awaitPointerEvent(pass = PointerEventPass.Initial)
                    val pointerCount = event.changes.size
                    val isZoomedIn = scale > MIN_SCALE

                    if (pointerCount >= 2 || isZoomedIn) {
                        val zoom = event.calculateZoom()
                        val pan = event.calculatePan()

                        scale = (scale * zoom).coerceIn(MIN_SCALE, MAX_SCALE)
                        offsetX += pan.x
                        offsetY += pan.y
                        if (scale == MIN_SCALE) {
                            offsetX = 0f
                            offsetY = 0f
                        }
                        event.changes.forEach { if (it.positionChanged()) it.consume() }
                    }
                } while (event.changes.any { it.pressed })
            }
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    scale = MIN_SCALE
                    offsetX = 0f
                    offsetY = 0f
                },
            )
        }
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            translationX = offsetX,
            translationY = offsetY,
            clip = true,
        )
}

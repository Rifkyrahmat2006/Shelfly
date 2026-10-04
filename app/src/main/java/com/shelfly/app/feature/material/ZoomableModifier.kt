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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

// PIC: Person C — Pinch-zoom + pan reusable, dipakai PDF viewer & Image viewer
// (user request: PDF bisa di-zoom, gambar bisa dilihat langsung in-app).
// Native gesture detection (foundation.gestures), tanpa library zoom eksternal.
//
// PENTING: hanya konsumsi gesture kalau (a) ada 2 jari (pinch) atau (b) sudah
// dalam kondisi zoom-in (scale > 1, perlu pan). Gesture 1 jari saat scale == 1
// TIDAK dikonsumsi, supaya swipe tetap diteruskan ke HorizontalPager di luar.
//
// PAN DI-CLAMP ke batas (size * (scale-1) / 2): tanpa ini, pan bisa geser
// konten sampai keluar box asalnya dan nutupi elemen lain di luar (Change
// File button, label halaman, Title field) — graphicsLayer(clip=true) cuma
// clip relatif ke posisi layer yang sudah ikut bergeser, bukan ke box asli.
private const val MIN_SCALE = 1f
private const val MAX_SCALE = 5f

@Composable
fun Modifier.zoomable(): Modifier {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var boxSize by remember { mutableFloatStateOf(0f) }
    var boxHeight by remember { mutableFloatStateOf(0f) }

    fun maxOffsetX() = (boxSize * (scale - 1f) / 2f).coerceAtLeast(0f)
    fun maxOffsetY() = (boxHeight * (scale - 1f) / 2f).coerceAtLeast(0f)

    return this
        .onSizeChanged { size: IntSize ->
            boxSize = size.width.toFloat()
            boxHeight = size.height.toFloat()
        }
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
                        offsetX = (offsetX + pan.x).coerceIn(-maxOffsetX(), maxOffsetX())
                        offsetY = (offsetY + pan.y).coerceIn(-maxOffsetY(), maxOffsetY())
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

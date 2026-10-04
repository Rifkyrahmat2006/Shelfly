package com.shelfly.app.feature.material

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi

// PIC: Person C — Preview material in-app (user request: upload material perlu
// preview, PDF bisa di-zoom, gambar bisa dilihat langsung). Dipakai di
// AddMaterialScreen (sebelum Save) dan MaterialDetailScreen (setelah Save).
// PDF: PdfPageRenderer native + pinch-zoom per halaman.
// Gambar: BitmapFactory native + pinch-zoom.
// Tipe lain: tidak ada preview in-app, fallback caller ke intent eksternal.
@Composable
fun MaterialPreview(uri: Uri, fileType: String, modifier: Modifier = Modifier) {
    when {
        fileType == "application/pdf" -> PdfInlineViewer(uri, modifier)
        fileType.startsWith("image/") -> ImageInlineViewer(uri, modifier)
        else -> Text("Preview tidak tersedia untuk tipe file ini.", modifier = modifier.padding(16.dp))
    }
}

@Composable
private fun ImageInlineViewer(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uri) {
        try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                bitmap = BitmapFactory.decodeStream(stream)
            } ?: run { error = "Tidak bisa membuka gambar" }
        } catch (e: Exception) {
            error = "Tidak bisa membuka gambar: ${e.message}"
        }
    }

    val current = bitmap
    when {
        error != null -> Text(error!!, modifier = modifier.padding(16.dp))
        current == null -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        else -> Image(
            bitmap = current.asImageBitmap(),
            contentDescription = "Preview gambar",
            modifier = modifier.fillMaxSize().zoomable(),
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PdfInlineViewer(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var renderer by remember { mutableStateOf<PdfPageRenderer?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uri) {
        try {
            renderer = PdfPageRenderer.fromUri(context, uri)
        } catch (e: Exception) {
            error = "Tidak bisa membuka PDF: ${e.message}"
        }
    }

    DisposableEffectCloseRenderer(renderer)

    val current = renderer
    when {
        error != null -> Text(error!!, modifier = modifier.padding(16.dp))
        current == null -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        else -> {
            val pagerState = rememberPagerState(pageCount = { current.pageCount })
            Box(modifier = modifier.fillMaxSize()) {
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                    val bitmap = remember(page) { current.renderPage(page) }
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Halaman ${page + 1}",
                        modifier = Modifier.fillMaxSize().zoomable(),
                    )
                }
                Text(
                    "Halaman ${pagerState.currentPage + 1} / ${current.pageCount}",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun DisposableEffectCloseRenderer(renderer: PdfPageRenderer?) {
    DisposableEffect(renderer) {
        onDispose { renderer?.close() }
    }
}


package com.shelfly.app.feature.material

import android.content.ActivityNotFoundException
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.net.Uri
import android.graphics.Bitmap
import com.shelfly.app.core.component.ConfirmationDialog
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.local.entity.MaterialEntity
import com.shelfly.app.data.repository.MaterialRepository
import kotlinx.coroutines.launch

// PIC: Person C — File Management: detail + open material (PRD section 24, 25)
// PDF di-render in-app via PdfPageRenderer (native, tanpa app eksternal). File
// lain (bukan PDF) tetap pakai buildOpenMaterialIntent -> delegasi ke app luar.
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MaterialDetailScreen(
    materialId: Long,
    onBack: () -> Unit = {},
    onDeleted: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember {
        MaterialRepository(ShelflyDatabase.getInstance(context).materialDao())
    }
    val viewModel = remember { MaterialDetailViewModel(repository) }
    val material by viewModel.material(materialId).collectAsState(initial = null)
    var openError by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(material?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Material")
                    }
                },
            )
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            val current = material
            if (current == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                if (current.fileType == "application/pdf") {
                    PdfInlineViewer(uri = Uri.parse(current.fileUri))
                } else {
                    Button(
                        onClick = {
                            try {
                                val intent = buildOpenMaterialIntent(Uri.parse(current.fileUri), current.fileType)
                                context.startActivity(intent)
                                openError = null
                                scope.launch { viewModel.markOpened(current) }
                            } catch (e: ActivityNotFoundException) {
                                openError = mapMaterialError(e)
                            }
                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        Text("Open")
                    }
                }
                if (openError != null) {
                    Text(openError!!, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }

    if (showDeleteDialog) {
        material?.let { current ->
            ConfirmationDialog(
                title = "Hapus Material",
                message = "\"${current.title}\" akan dihapus permanen. Lanjutkan?",
                onConfirm = {
                    showDeleteDialog = false
                    scope.launch {
                        repository.delete(current)
                        onDeleted()
                    }
                },
                onDismiss = { showDeleteDialog = false },
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PdfInlineViewer(uri: Uri) {
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
        error != null -> Text(error!!, modifier = Modifier.padding(16.dp))
        current == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        else -> {
            val pagerState = rememberPagerState(pageCount = { current.pageCount })
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    "Halaman ${pagerState.currentPage + 1} / ${current.pageCount}",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                    val bitmap = remember(page) { current.renderPage(page) }
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Halaman ${page + 1}",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Composable
private fun DisposableEffectCloseRenderer(renderer: PdfPageRenderer?) {
    androidx.compose.runtime.DisposableEffect(renderer) {
        onDispose { renderer?.close() }
    }
}

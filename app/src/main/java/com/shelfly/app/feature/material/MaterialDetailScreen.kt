package com.shelfly.app.feature.material

import android.content.ActivityNotFoundException
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.net.Uri
import com.shelfly.app.data.local.ShelflyDatabase
import com.shelfly.app.data.repository.MaterialRepository
import kotlinx.coroutines.launch

// PIC: Person C — File Management: detail + open material (PRD section 24, 25)
// PDF & gambar di-render in-app via MaterialPreview (native, tanpa app
// eksternal, bisa di-zoom). Tipe lain tetap pakai buildOpenMaterialIntent.
@Composable
fun MaterialDetailScreen(materialId: Long) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember {
        MaterialRepository(ShelflyDatabase.getInstance(context).materialDao())
    }
    val viewModel = remember { MaterialDetailViewModel(repository) }
    val material by viewModel.material(materialId).collectAsState(initial = null)
    var openError by remember { mutableStateOf<String?>(null) }

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            val current = material
            if (current == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Text(current.title, modifier = Modifier.padding(16.dp))
                val isPreviewable = current.fileType == "application/pdf" || current.fileType.startsWith("image/")
                if (isPreviewable) {
                    MaterialPreview(
                        uri = Uri.parse(current.fileUri),
                        fileType = current.fileType,
                        modifier = Modifier.fillMaxSize(),
                    )
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
}


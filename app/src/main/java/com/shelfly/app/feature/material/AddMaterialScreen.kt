package com.shelfly.app.feature.material

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shelfly.app.R
import com.shelfly.app.core.theme.Spacing
import kotlinx.coroutines.launch

// PIC: Person C — Add Material Flow (design.md §5): step 1 system file picker,
// step 2 preview + metadata form, Save. Shelf dropdown jadi tanggung jawab
// Nadine (A) saat integrasi UI penuh — screen ini pakai shelfId yang sudah
// diketahui (dipanggil dari Shelf Detail "Add Material" FAB).
// Bulk upload (user request): picker multi-file (OpenMultipleDocuments). Pilih
// 1 file -> flow normal (preview + edit title). Pilih >1 file -> tampilkan
// daftar preview (nama file asli per-item) dulu, baru commit semua saat user
// tekan "Import N File" (bukan auto-commit tanpa preview seperti sebelumnya).
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddMaterialScreen(
    viewModel: AddMaterialViewModel,
    shelfId: Long,
    onSaved: () -> Unit,
    onBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val title by viewModel.title
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileType by remember { mutableStateOf("") }
    var bulkUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var bulkInProgress by remember { mutableStateOf(false) }
    var bulkResultMessage by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        when {
            uris.isEmpty() -> Unit
            uris.size == 1 -> {
                viewModel.onFileSelected(uris[0])
                viewModel.onShelfSelected(shelfId)
                selectedUri = uris[0]
                selectedFileType = viewModel.fileType
                bulkUris = emptyList()
                bulkResultMessage = null
            }
            else -> {
                selectedUri = null
                bulkUris = uris
                bulkResultMessage = null
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Material") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            if (bulkInProgress) {
                CircularProgressIndicator()
                Text("Mengimport file...")
            } else if (bulkUris.isNotEmpty()) {
                Text("${bulkUris.size} file dipilih:")
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(bulkUris) { uri ->
                        val metadata = remember(uri) { extractFileMetadata(context, uri) }
                        ListItem(
                            headlineContent = { Text(metadata.title) },
                            supportingContent = { Text(metadata.fileType) },
                        )
                    }
                }
                Button(
                    onClick = {
                        bulkInProgress = true
                        scope.launch {
                            val count = viewModel.saveBulk(bulkUris, shelfId)
                            bulkInProgress = false
                            bulkUris = emptyList()
                            bulkResultMessage = "$count file berhasil diimport."
                            onSaved()
                        }
                    },
                ) {
                    Text("Import ${bulkUris.size} File")
                }
            } else if (selectedUri == null) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.illustration_upload),
                            contentDescription = "Upload illustration",
                            modifier = Modifier.height(200.dp),
                        )
                        Button(onClick = { launcher.launch(arrayOf("*/*")) }) {
                            Text("Choose File (bisa pilih banyak)")
                        }
                    }
                }
            } else {
                Button(onClick = { launcher.launch(arrayOf("*/*")) }) {
                    Text("Change File")
                }
                MaterialPreview(
                    uri = selectedUri!!,
                    fileType = selectedFileType,
                    modifier = Modifier.fillMaxWidth().height(320.dp),
                )
            }
            if (bulkResultMessage != null) {
                Text(bulkResultMessage!!)
            }
            if (title.isNotBlank() && selectedUri != null) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { viewModel.title.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                viewModel.save()
                                onSaved()
                            } catch (e: IllegalStateException) {
                                errorMessage = e.message
                            }
                        }
                    },
                ) {
                    Text("Save")
                }
            }
            if (errorMessage != null) {
                Text(errorMessage!!)
            }
        }
    }
}

package com.shelfly.app.feature.material

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shelfly.app.core.theme.Spacing
import kotlinx.coroutines.launch

// PIC: Person C — Add Material Flow (design.md §5): step 1 system file picker,
// step 2 preview + metadata form, Save. Shelf dropdown jadi tanggung jawab
// Nadine (A) saat integrasi UI penuh — screen ini pakai shelfId yang sudah
// diketahui (dipanggil dari Shelf Detail "Add Material" FAB).
@Composable
fun AddMaterialScreen(
    viewModel: AddMaterialViewModel,
    shelfId: Long,
    onSaved: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val title by viewModel.title
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileType by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            viewModel.onFileSelected(uri)
            viewModel.onShelfSelected(shelfId)
            selectedUri = uri
            selectedFileType = viewModel.fileType
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Text("Add Material")
            Button(onClick = { launcher.launch(arrayOf("*/*")) }) {
                Text(if (title.isBlank()) "Choose File" else "Change File")
            }
            val previewUri = selectedUri
            if (previewUri != null) {
                MaterialPreview(
                    uri = previewUri,
                    fileType = selectedFileType,
                    modifier = Modifier.fillMaxWidth().height(320.dp),
                )
            }
            if (title.isNotBlank()) {
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

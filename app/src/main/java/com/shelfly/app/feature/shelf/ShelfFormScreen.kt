package com.shelfly.app.feature.shelf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.shelfly.app.core.theme.Spacing
import kotlinx.coroutines.launch

// PIC: Person A — Create/Edit Shelf bottom sheet form (PRD section 17)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ShelfFormScreen(
    viewModel: ShelfFormViewModel,
    isEditMode: Boolean,
    onSaved: () -> Unit,
    onBack: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val name by viewModel.name
    val description by viewModel.description
    val errorMessage by viewModel.errorMessage

    LaunchedEffect(Unit) {
        if (isEditMode) viewModel.loadExisting()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Shelf" else "Create Shelf") },
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
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.name.value = it },
                label = { Text("Nama Shelf") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null,
            )
            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.description.value = it },
                label = { Text("Deskripsi (opsional)") },
                modifier = Modifier.fillMaxWidth(),
            )
            if (errorMessage != null) {
                Text(errorMessage!!)
            }
            Button(
                onClick = {
                    scope.launch {
                        if (viewModel.save()) onSaved()
                    }
                }
            ) {
                Text("Save")
            }
        }
    }
}

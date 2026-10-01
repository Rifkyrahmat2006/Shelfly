package com.shelfly.app.core.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shelfly.app.core.theme.Spacing

// PIC: Person A (Nadine) - Create/Edit Shelf bottom sheet (PRD section 17, TASKS.md A5)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditShelfSheet(
    onDismiss: () -> Unit,
    onSave: (name: String, description: String?, icon: String?) -> Unit,
    initialName: String = "",
    initialDescription: String = "",
    initialIcon: String = "LibraryBooks",
    isEditing: Boolean = false,
    onDelete: (() -> Unit)? = null,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    var name by remember { mutableStateOf(initialName) }
    var description by remember { mutableStateOf(initialDescription) }
    var icon by remember { mutableStateOf(initialIcon) }
    var nameError by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.lg)
                .padding(bottom = Spacing.xl),
        ) {
            Text(
                text = if (isEditing) "Edit Shelf" else "Create New Shelf",
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (ShelfInputValidation.isValidName(it)) nameError = null
                },
                label = { Text("Shelf Name *") },
                isError = nameError != null,
                supportingText = nameError?.let { { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (Optional)") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            Row(modifier = Modifier.fillMaxWidth()) {
                if (isEditing && onDelete != null) {
                    OutlinedButton(
                        onClick = onDelete,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error,
                        ),
                    ) {
                        Text("Delete")
                    }
                    Spacer(modifier = Modifier.width(Spacing.md))
                }

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(Spacing.md))

                Button(
                    onClick = {
                        if (!ShelfInputValidation.isValidName(name)) {
                            nameError = "Shelf name cannot be empty"
                        } else {
                            onSave(name.trim(), description.trim().ifEmpty { null }, icon)
                        }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(if (isEditing) "Save" else "Create")
                }
            }
        }
    }
}

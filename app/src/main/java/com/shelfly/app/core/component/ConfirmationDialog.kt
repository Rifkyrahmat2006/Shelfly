package com.shelfly.app.core.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

// PIC: Person A - Confirmation dialog reusable, dipakai buat Delete Shelf/Material (PRD section 27)
@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmLabel: String = "Delete",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isDestructive: Boolean = true,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmLabel,
                    color = if (isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}

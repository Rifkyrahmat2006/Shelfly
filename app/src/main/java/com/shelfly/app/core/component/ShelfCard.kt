package com.shelfly.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shelfly.app.core.theme.Radius
import com.shelfly.app.core.theme.Spacing

// PIC: Person A - Shelf Card, dipakai di Home & Shelves list (PRD section 41 Material Card, adaptasi Shelf)
@Composable
fun ShelfCard(
    name: String,
    materialCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(Radius.md))
            .padding(Spacing.lg),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.LibraryBooks,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp),
            )
            if (onEdit != null) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            if (onDelete != null) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = Spacing.sm),
        )
        Text(
            text = "$materialCount materials",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
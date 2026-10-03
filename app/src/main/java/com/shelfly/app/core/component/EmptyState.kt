package com.shelfly.app.core.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.shelfly.app.core.theme.Spacing

// PIC: Person A - Empty states reusable (PRD section 28)
@Composable
fun EmptyState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    ctaLabel: String? = null,
    onCtaClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.xl),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Spacing.sm),
        )
        if (ctaLabel != null) {
            Button(onClick = onCtaClick, modifier = Modifier.padding(top = Spacing.lg)) {
                Text(ctaLabel)
            }
        }
    }
}

package com.shelfly.app.feature.filter

import com.shelfly.app.data.local.entity.MaterialEntity

// PIC: Person D — Filter state (PRD section 20). AND antar kategori (Shelf/Type/Category),
// OR dalam kategori yang sama (multi-select). Set kosong = tidak difilter (match semua).
data class FilterState(
    val shelfIds: Set<Long> = emptySet(),
    val fileTypes: Set<String> = emptySet(),
    val categoryIds: Set<Long> = emptySet(),
) {
    fun apply(materials: List<MaterialEntity>): List<MaterialEntity> = materials.filter { m ->
        (shelfIds.isEmpty() || m.shelfId in shelfIds) &&
            (fileTypes.isEmpty() || m.fileType in fileTypes) &&
            (categoryIds.isEmpty() || m.categoryId in categoryIds)
    }
}

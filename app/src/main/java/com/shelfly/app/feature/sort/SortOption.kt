package com.shelfly.app.feature.sort

import com.shelfly.app.data.local.entity.MaterialEntity

// PIC: Person D — Sort options (PRD section 21). Hanya mengubah urutan tampilan,
// tidak mengubah data di database.
enum class SortOption {
    NAME_A_Z,
    NAME_Z_A,
    NEWEST,
    OLDEST,
    RECENTLY_OPENED,
    ;

    fun sort(materials: List<MaterialEntity>): List<MaterialEntity> = when (this) {
        NAME_A_Z -> materials.sortedBy { it.title.lowercase() }
        NAME_Z_A -> materials.sortedByDescending { it.title.lowercase() }
        NEWEST -> materials.sortedByDescending { it.createdAt }
        OLDEST -> materials.sortedBy { it.createdAt }
        RECENTLY_OPENED -> {
            val (opened, neverOpened) = materials.partition { it.lastOpenedAt != null }
            opened.sortedByDescending { it.lastOpenedAt } + neverOpened
        }
    }
}

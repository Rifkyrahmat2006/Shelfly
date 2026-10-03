package com.shelfly.app.feature.sort

import com.shelfly.app.data.local.entity.MaterialEntity
import org.junit.Test
import org.junit.Assert.assertEquals

class SortOptionTest {

    private val materialA = MaterialEntity(
        id = 1, title = "Zebra", fileUri = "u1", fileType = "pdf", fileSize = 100L,
        shelfId = 1L, createdAt = 300L, lastOpenedAt = 10L,
    )
    private val materialB = MaterialEntity(
        id = 2, title = "Apple", fileUri = "u2", fileType = "pdf", fileSize = 200L,
        shelfId = 1L, createdAt = 100L, lastOpenedAt = 30L,
    )
    private val materialC = MaterialEntity(
        id = 3, title = "Mango", fileUri = "u3", fileType = "pdf", fileSize = 300L,
        shelfId = 1L, createdAt = 200L, lastOpenedAt = null,
    )
    private val all = listOf(materialA, materialB, materialC)

    @Test
    fun nameAZ_sortsAlphabetically() {
        val result = SortOption.NAME_A_Z.sort(all)
        assertEquals(listOf(materialB, materialC, materialA), result)
    }

    @Test
    fun nameZA_sortsReverseAlphabetically() {
        val result = SortOption.NAME_Z_A.sort(all)
        assertEquals(listOf(materialA, materialC, materialB), result)
    }

    @Test
    fun newest_sortsByCreatedAtDescending() {
        val result = SortOption.NEWEST.sort(all)
        assertEquals(listOf(materialA, materialC, materialB), result)
    }

    @Test
    fun oldest_sortsByCreatedAtAscending() {
        val result = SortOption.OLDEST.sort(all)
        assertEquals(listOf(materialB, materialC, materialA), result)
    }

    @Test
    fun recentlyOpened_sortsByLastOpenedAtDescendingNullsLast() {
        val result = SortOption.RECENTLY_OPENED.sort(all)
        assertEquals(listOf(materialB, materialA, materialC), result)
    }
}

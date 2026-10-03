package com.shelfly.app.feature.filter

import com.shelfly.app.data.local.entity.MaterialEntity
import org.junit.Test
import org.junit.Assert.assertEquals

class FilterStateTest {

    private val materialA = MaterialEntity(
        id = 1, title = "A", fileUri = "u1", fileType = "pdf",
        fileSize = 100L, shelfId = 1L, categoryId = 10L,
    )
    private val materialB = MaterialEntity(
        id = 2, title = "B", fileUri = "u2", fileType = "docx",
        fileSize = 200L, shelfId = 2L, categoryId = 20L,
    )
    private val materialC = MaterialEntity(
        id = 3, title = "C", fileUri = "u3", fileType = "pdf",
        fileSize = 300L, shelfId = 1L, categoryId = 20L,
    )
    private val all = listOf(materialA, materialB, materialC)

    @Test
    fun emptyFilter_returnsAll() {
        val filter = FilterState()
        assertEquals(all, filter.apply(all))
    }

    @Test
    fun filterByShelf_singleShelf_returnsMatching() {
        val filter = FilterState(shelfIds = setOf(1L))
        assertEquals(listOf(materialA, materialC), filter.apply(all))
    }

    @Test
    fun filterByFileType_returnsMatching() {
        val filter = FilterState(fileTypes = setOf("pdf"))
        assertEquals(listOf(materialA, materialC), filter.apply(all))
    }

    @Test
    fun filterByCategory_returnsMatching() {
        val filter = FilterState(categoryIds = setOf(20L))
        assertEquals(listOf(materialB, materialC), filter.apply(all))
    }

    @Test
    fun combinedFilter_shelfAndType_isAndAcrossCategories() {
        val filter = FilterState(shelfIds = setOf(1L), fileTypes = setOf("pdf"))
        assertEquals(listOf(materialA, materialC), filter.apply(all))
    }

    @Test
    fun multiSelectWithinSameCategory_isOr() {
        val filter = FilterState(shelfIds = setOf(1L, 2L))
        assertEquals(all, filter.apply(all))
    }

    @Test
    fun noMatch_returnsEmptyList() {
        val filter = FilterState(shelfIds = setOf(999L))
        assertEquals(emptyList<MaterialEntity>(), filter.apply(all))
    }
}

package com.shelfly.app.feature.search

import com.shelfly.app.feature.filter.FilterState
import com.shelfly.app.feature.sort.SortOption
import org.junit.Test
import org.junit.Assert.assertEquals

class SearchFilterStateHolderTest {

    @Test
    fun initialState_isDefaultEmpty() {
        val holder = SearchFilterStateHolder()
        assertEquals("", holder.query)
        assertEquals(FilterState(), holder.filter)
        assertEquals(SortOption.NEWEST, holder.sort)
    }

    @Test
    fun updateQuery_persistsAcrossReads() {
        val holder = SearchFilterStateHolder()
        holder.updateQuery("database")
        assertEquals("database", holder.query)
        // simulasi "navigasi balik": baca ulang tanpa reset instance
        assertEquals("database", holder.query)
    }

    @Test
    fun updateFilter_persistsAcrossReads() {
        val holder = SearchFilterStateHolder()
        val filter = FilterState(shelfIds = setOf(1L))
        holder.updateFilter(filter)
        assertEquals(filter, holder.filter)
    }

    @Test
    fun updateSort_persistsAcrossReads() {
        val holder = SearchFilterStateHolder()
        holder.updateSort(SortOption.NAME_A_Z)
        assertEquals(SortOption.NAME_A_Z, holder.sort)
    }

    @Test
    fun reset_clearsAllToDefault() {
        val holder = SearchFilterStateHolder()
        holder.updateQuery("database")
        holder.updateFilter(FilterState(shelfIds = setOf(1L)))
        holder.updateSort(SortOption.OLDEST)

        holder.reset()

        assertEquals("", holder.query)
        assertEquals(FilterState(), holder.filter)
        assertEquals(SortOption.NEWEST, holder.sort)
    }
}
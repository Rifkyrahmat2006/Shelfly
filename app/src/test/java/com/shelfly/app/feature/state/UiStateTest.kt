package com.shelfly.app.feature.state

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class UiStateTest {

    @Test
    fun loading_isInstanceOfUiState() {
        val state: UiState<String> = UiState.Loading
        assertTrue(state is UiState.Loading)
    }

    @Test
    fun success_holdsData() {
        val state: UiState<List<String>> = UiState.Success(listOf("a", "b"))
        assertTrue(state is UiState.Success)
        assertEquals(listOf("a", "b"), (state as UiState.Success).data)
    }

    @Test
    fun empty_isInstanceOfUiState() {
        val state: UiState<String> = UiState.Empty
        assertTrue(state is UiState.Empty)
    }

    @Test
    fun error_holdsMessage() {
        val state: UiState<String> = UiState.Error("Gagal memuat data")
        assertTrue(state is UiState.Error)
        assertEquals("Gagal memuat data", (state as UiState.Error).message)
    }

    @Test
    fun successWithEmptyList_isDistinctFromEmptyState() {
        val success: UiState<List<String>> = UiState.Success(emptyList())
        assertTrue(success is UiState.Success)
        assertTrue((success as UiState.Success).data.isEmpty())
    }
}

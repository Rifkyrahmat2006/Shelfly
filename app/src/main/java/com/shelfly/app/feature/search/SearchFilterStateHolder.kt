package com.shelfly.app.feature.search

import com.shelfly.app.feature.filter.FilterState
import com.shelfly.app.feature.sort.SortOption

// PIC: Person D — Preserve search/filter state saat navigasi balik (design.md §22).
// Dipegang di scope ViewModel (bukan Composable local state), jadi otomatis tidak
// reset saat navigate back selama ViewModel instance-nya sama (default Navigation Compose).
class SearchFilterStateHolder {
    var query: String = ""
        private set
    var filter: FilterState = FilterState()
        private set
    var sort: SortOption = SortOption.NEWEST
        private set

    fun updateQuery(newQuery: String) {
        query = newQuery
    }

    fun updateFilter(newFilter: FilterState) {
        filter = newFilter
    }

    fun updateSort(newSort: SortOption) {
        sort = newSort
    }

    fun reset() {
        query = ""
        filter = FilterState()
        sort = SortOption.NEWEST
    }
}
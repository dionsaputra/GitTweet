package ds.gittweet.ui.main.searchuser

import javax.inject.Inject

class SearchUserViewState @Inject constructor() {

    var lastSearchQuery: String = ""
    var isLoadingMoreSearchResult: Boolean = false
    var isSearchReachEndOfPage: Boolean = false
    var currentSearchPage: Int = 1
    val VISIBLE_THRESHOLD: Int = 5

    fun isSearchRecyclerAbleToLoad() = !isLoadingMoreSearchResult && !isSearchReachEndOfPage

    fun resetCurrentSearchPage() {
        this.currentSearchPage = 1
    }
}
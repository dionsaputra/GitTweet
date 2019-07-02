package ds.gittweet.ui.main.user.search.view

import javax.inject.Inject

class UserSearchViewState @Inject constructor() {

    var lastSearchQuery: String = ""
    var isLoadingMoreSearchResult: Boolean = false
    var isSearchReachEndOfPage: Boolean = false
    var currentSearchPage: Int =
        DEFAULT_REMOTE_SEARCH_PAGE
    var isFirstRemoteSearch: Boolean = true

    fun isSearchRecyclerAbleToLoad(): Boolean {
        return !isLoadingMoreSearchResult && !isSearchReachEndOfPage
    }

    fun resetCurrentSearchPage() {
        this.currentSearchPage =
            DEFAULT_REMOTE_SEARCH_PAGE
    }

    companion object {
        private const val DEFAULT_REMOTE_SEARCH_PAGE = 1
        const val REMOTE_SEARCH_VISIBLE_THRESHOLD = 5
        const val REMOTE_SEARCH_PAGINATION_SIZE = 30
    }
}
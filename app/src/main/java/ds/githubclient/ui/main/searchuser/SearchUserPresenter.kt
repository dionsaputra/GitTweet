package ds.githubclient.ui.main.searchuser

import ds.githubclient.util.orZero

class SearchUserPresenter {

    lateinit var view: SearchUserView
    private val interactor = SearchUserInteractor()

    companion object {
        const val SEARCH_DEFAULT_PAGE = 1
        const val SEARCH_PAGINATION_SIZE = 30
    }

    private var currentSearchPage = 1

    fun attachView(view: SearchUserView) {
        this.view = view
        view.initView()
    }

    fun onBackNavigationClick() {
        view.closeView()
    }

    fun onSearchQueryChange(query: String) {
        resetSearchState()

        if (query.isEmpty()) {
            view.hideClearSearchButton()
            view.showGroupRecent()
            view.hideGroupSearch()
            listRecent()
        } else {
            view.showClearSearchButton()
            view.hideGroupRecent()
            view.showGroupSearch()
            listSearch(query, currentSearchPage, SEARCH_PAGINATION_SIZE, true)
        }
    }

    fun onClearSearch() {
        view.clearSearchQuery()
    }

    fun onClearRecent() {
        view.showDeleteRecentConfirmation()
    }

    fun onAcceptClearRecent() {
        // TODO: call interactor clear recent
    }

    fun onCancelClearRecent() {
        view.hideDeleteRecentConfirmation()
    }

    fun loadMoreSearch(lastSearchQuery: String) {
        listSearch(lastSearchQuery, currentSearchPage, SEARCH_PAGINATION_SIZE, false)
    }

    private fun resetSearchState() {
        currentSearchPage = SEARCH_DEFAULT_PAGE
    }

    private fun listRecent() {
        interactor.listRecentSearch() { users, error ->
            if (error == null) {
                if (users.isNullOrEmpty()) {
                    view.showRecentEmpty()
                } else {
                    view.showRecentResult(users)
                }
            } else {
                view.showError(error)
            }
        }
    }

    private fun listSearch(query: String, page: Int, perPage: Int, isFirstSearch: Boolean) {
        if (!isFirstSearch) {
            view.showLoadMoreSearchLoading()
        }

        interactor.search(query, page, perPage) { searchResponse, error ->
            if (error == null) {
                val users = searchResponse?.items.orEmpty()
                if (users.isNullOrEmpty()) {
                    view.showSearchEmpty()
                } else {
                    if (isFirstSearch) {
                        view.showFirstPageSearchResult(users)
                    } else {
                        view.showNextPageSearchResult(users)
                        view.hideLoadMoreSearchLoading()
                    }

                    view.checkEndOfSearchData(searchResponse?.totalCount.orZero())
                    currentSearchPage++
                }
            } else {
                view.showError(error)
            }
        }
    }
}
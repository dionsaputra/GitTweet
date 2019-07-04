package ds.gittweet.ui.main.user.search.presenter

import ds.gittweet.data.remote.response.SearchResponse
import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.ui.main.user.search.view.UserSearchView
import ds.gittweet.ui.main.user.search.view.UserSearchViewState
import ds.gittweet.helper.applyScheduler
import ds.gittweet.helper.orZero
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserSearchPresenter @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val interactor: UserSearchInteractor
) {

    private lateinit var view: UserSearchView

    fun attachView(view: UserSearchView) {
        this.view = view
        view.initView()

        listLocalUser()
    }

    fun onBackNavigationClick() {
        view.closeView()
    }

    fun onSearchQueryChange(query: String) {
        resetSearchState()

        if (query.isEmpty()) {
            view.showClearSearchButton(false)
            view.showGroupRecent(true)
            view.showGroupSearch(false)
            listLocalUser()
        } else {
            view.showClearSearchButton(true)
            view.showGroupRecent(false)
            view.showGroupSearch(true)
            listRemoteUser(query, view.getState().currentSearchPage)
        }
    }

    fun onClearSearch() {
        view.clearSearchQuery()
    }

    fun onClearRecent() {
        view.showClearRecentConfirmation(true)
    }

    fun onAcceptClearRecent() {
        // TODO: call interactor clear recent
    }

    fun onCancelClearRecent() {
        view.showClearRecentConfirmation(false)
    }

    fun loadMoreSearch() {
        view.getState().isFirstRemoteSearch = false
        listRemoteUser(view.getState().lastSearchQuery, view.getState().currentSearchPage)
    }

    fun onRemoteUserClick(userResponse: UserResponse) {
        compositeDisposable.add(
            interactor.insertRecent(userResponse)
                .applyScheduler()
                .subscribe {
                    view.showUserDetail(userResponse.login.orEmpty())
                }
        )
    }

    private fun resetSearchState() {
        view.getState().resetCurrentSearchPage()
        view.getState().isFirstRemoteSearch = true
    }

    private fun listLocalUser() {
        compositeDisposable.add(
            interactor.listLocalUser().applyScheduler().subscribe { view.showRecentResult(it) }
        )
    }

    private fun listRemoteUser(query: String, page: Int) {
        compositeDisposable.clear()
        compositeDisposable.add(
            interactor.search(query, page, UserSearchViewState.REMOTE_SEARCH_PAGINATION_SIZE)
                .applyScheduler()
                .doOnSubscribe { view.showLoadMoreSearchLoading(true) }
                .doOnTerminate { view.showLoadMoreSearchLoading(false) }
                .subscribe(
                    { handleSuccessSearch(it) },
                    { handleErrorResponse(it) }
                )
        )
    }

    private fun handleSuccessSearch(searchUserResponse: SearchResponse<List<UserResponse>?>) {
        val users = searchUserResponse.items.orEmpty()
        if (users.isNullOrEmpty()) {
            view.showSearchEmpty()
        } else {
            view.showSearchResult(users)
            view.checkEndOfSearchData(searchUserResponse.totalCount.orZero())
            view.getState().currentSearchPage++
        }
    }

    private fun handleErrorResponse(error: Throwable) {
        error.message?.let { view.showMessage(it) }
    }

    fun detachView() {
        compositeDisposable.dispose()
    }
}
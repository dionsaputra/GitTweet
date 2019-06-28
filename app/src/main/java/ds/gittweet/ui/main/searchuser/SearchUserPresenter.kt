package ds.gittweet.ui.main.searchuser

import android.widget.Toast
import ds.gittweet.data.remote.response.SearchResponse
import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.util.orZero
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchUserPresenter @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val interactor: SearchUserInteractor) {

    lateinit var view: SearchUserView

    companion object {
        const val SEARCH_DEFAULT_PAGE = 1
        const val SEARCH_PAGINATION_SIZE = 30
    }

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
            view.showClearSearchButton(false)
            view.showGroupRecent(true)
            view.showGroupSearch(false)
            listRecent()
        } else {
            view.showClearSearchButton(true)
            view.showGroupRecent(false)
            view.showGroupSearch(true)
            listSearch(query, view.getState().currentSearchPage, SEARCH_PAGINATION_SIZE, true)
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
        listSearch(view.getState().lastSearchQuery, view.getState().currentSearchPage, SEARCH_PAGINATION_SIZE, false)
    }

    fun onSearchItemClick(userResponse: UserResponse) {
        view.showMessage(userResponse.login + " has been clicked!")
        compositeDisposable.add(
            interactor.insertRecent(userResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.showMessage("Success insert to database")
                }
        )
    }

    private fun resetSearchState() {
        view.getState().resetCurrentSearchPage()
    }

    private fun listRecent() {
//        interactor.listRecentSearch() { users, error ->
//            if (error == null) {
//                if (users.isNullOrEmpty()) {
//                    view.showRecentEmpty()
//                } else {
//                    view.showRecentResult(users)
//                }
//            } else {
//                error.message?.let { view.showMessage(it) }
//            }
//        }
    }

    private fun listSearch(query: String, page: Int, perPage: Int, isFirstSearch: Boolean) {
        if (!isFirstSearch) {
            view.showLoadMoreSearchLoading(true)
        }

        compositeDisposable.clear()

        compositeDisposable.add(
            interactor.search(query, page, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { handleSuccessSearch(it, isFirstSearch) },
                    { handleErrorResponse(it) }
                )
        )
    }

    private fun handleSuccessSearch(searchUserResponse: SearchResponse<List<UserResponse>?>, isFirstSearch: Boolean) {
        val users = searchUserResponse.items.orEmpty()
        if (users.isNullOrEmpty()) {
            view.showSearchEmpty()
        } else {
            view.showSearchResult(users, isFirstSearch)

            view.checkEndOfSearchData(searchUserResponse.totalCount.orZero())
            view.getState().currentSearchPage++
        }

        if (!isFirstSearch) {
            view.showLoadMoreSearchLoading(false)
        }
    }

    private fun handleErrorResponse(error: Throwable) {
        error.message?.let { view.showMessage(it) }
    }

    fun detachView() {
        compositeDisposable.dispose()
    }
}
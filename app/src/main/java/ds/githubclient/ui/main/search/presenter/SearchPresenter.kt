package ds.githubclient.ui.main.search.presenter

import ds.githubclient.data.network.model.User
import ds.githubclient.ui.main.search.interactor.SearchInteractor
import ds.githubclient.ui.main.search.view.SearchMvpView
import ds.githubclient.util.orFalse
import ds.githubclient.util.orZero
import kotlin.math.sin

class SearchPresenter : SearchMvpPresenter {

    lateinit var view: SearchMvpView
    private var interactor: SearchInteractor = SearchInteractor()
    private var since = 0L
    private var page = 1

    companion object {
        private var PAGINATION_SIZE = 30
    }

    override fun attachView(view: SearchMvpView) {
        this.view = view
    }

    override fun getUsers(searchQuery: String, perPage: Int, isStartingPage: Boolean) {
        showProgress(isStartingPage, isFinish = false)
        if (searchQuery.isEmpty()) {
            getAllUser(perPage, isStartingPage)
        } else {
            searchUser(searchQuery, perPage, isStartingPage)
        }
    }

    private fun getAllUser(perPage: Int, isStartingPage: Boolean) {
        interactor.getAllUser(since, perPage) { users, throwable ->
            onUserResponseReceived(isStartingPage, false, users, throwable)
        }
    }

    private fun searchUser(searchQuery: String, perPage: Int, isStartingPage: Boolean) {
        interactor.searchUser(searchQuery, page, perPage) { searchResponse, throwable ->
            if(view.getTotalItem() >= searchResponse?.totalCount?:0) {
                view.onReachEndOfData()
            }
            onUserResponseReceived(isStartingPage, true, searchResponse?.items, throwable)
        }
    }

    private fun showProgress(isStartingPage: Boolean, isFinish: Boolean) {
        if (isStartingPage) {
            view.onRefresh(!isFinish)
            since = 0
            page = 1
        } else {
            view.onLoadMore(!isFinish)
        }
    }

    private fun onUserResponseReceived(isStartingPage: Boolean, isSearch: Boolean, users: List<User>?, throwable: Throwable?) {
        showProgress(isStartingPage, isFinish = true)
        if (users.isNullOrEmpty()) {
            view.showEmptyUser()
        } else {
            if (isStartingPage) {
                view.showInitialPageUsers(users)
            } else {
                view.showNextPageUsers(users)
            }

            if (isSearch) {
                page++
            } else {
                since = users.last().id.orZero()
            }
        }

        if (throwable?.message?.isNotEmpty().orFalse()) {
            view.showErrorMessage(throwable?.message.orEmpty())
        }
    }
}
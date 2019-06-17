package ds.githubclient.ui.main.search.presenter

import ds.githubclient.ui.main.search.interactor.SearchInteractor
import ds.githubclient.ui.main.search.view.SearchMvpView
import ds.githubclient.util.orZero

class SearchPresenter : SearchMvpPresenter {

    lateinit var view: SearchMvpView
    private var interactor: SearchInteractor = SearchInteractor()
    private var lastUserId = INITIAL_USER_ID

    companion object {
        private const val INITIAL_PAGE_NUMBER = 0
        private const val PAGINATION_SIZE = 30
        private const val INITIAL_USER_ID: Long = 1
    }

    override fun attachView(view: SearchMvpView) {
        this.view = view
    }

    override fun filterUsers(searchQuery: String) {
//        interactor.getAllUsers(searchQuery) { users, errorMessage -> onUserResponseReceived(users, errorMessage) }
    }

    override fun loadInitialUsers() {
        view.onRefresh(true)
        interactor.getAllUsers(INITIAL_USER_ID, PAGINATION_SIZE) { users, errorMessage ->
            view.onRefresh(false)
            if (users.isNullOrEmpty()) {
                view.showEmptyUser()
            } else {
                lastUserId = users.last().id.orZero()
                view.showInitialPageUsers(users)
            }

            errorMessage?.let { view.showErrorMessage(it) }
        }
    }

    override fun loadMoreUsers() {
        view.onLoadMore(true)
        interactor.getAllUsers(lastUserId + 1, PAGINATION_SIZE) { users, errorMessage ->
            view.onLoadMore(false)
            if (users.isNullOrEmpty()) {
                view.showEmptyUser()
            } else {
                lastUserId = users.last().id.orZero()
                view.showNextPageUsers(users)
            }

            errorMessage?.let { view.showErrorMessage(it) }
        }
    }
}
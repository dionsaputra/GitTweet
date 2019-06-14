package ds.githubclient.ui.main.search.impl

import ds.githubclient.data.network.model.User
import ds.githubclient.ui.main.search.contract.SearchMvpInteractor
import ds.githubclient.ui.main.search.contract.SearchMvpPresenter
import ds.githubclient.ui.main.search.contract.SearchMvpView

class SearchPresenter : SearchMvpPresenter {

    lateinit var view: SearchMvpView
    var interactor: SearchMvpInteractor = SearchInteractor()

    override fun attachView(view: SearchMvpView) {
        this.view = view
    }

    override fun getAllUsers() {
        interactor.getAllUsers() { users, errorMessage -> onUserResponseReceived(users, errorMessage) }
    }

    override fun filterUsers(searchQuery: String) {
//        interactor.getAllUsers(searchQuery) { users, errorMessage -> onUserResponseReceived(users, errorMessage) }
    }

    private fun onUserResponseReceived(users: List<User>?, errorMessage: String?) {
        if (users.isNullOrEmpty()) {
            view.showEmptyUser()
        } else {
            view.showAllUser(users)
        }
        errorMessage?.let { view.showErrorMessage(it) }
    }
}
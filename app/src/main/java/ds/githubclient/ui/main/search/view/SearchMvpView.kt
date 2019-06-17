package ds.githubclient.ui.main.search.view

import ds.githubclient.data.network.model.User

interface SearchMvpView {

    fun showInitialPageUsers(users: List<User>)
    fun showNextPageUsers(users: List<User>)
    fun onRefresh(refreshState: Boolean)
    fun onLoadMore(loadingMoreState: Boolean)
    fun showEmptyUser()
    fun showErrorMessage(errorMessage: String)
}
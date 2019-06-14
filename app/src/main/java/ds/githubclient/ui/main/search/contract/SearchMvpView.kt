package ds.githubclient.ui.main.search.contract

import ds.githubclient.data.network.model.User

interface SearchMvpView {

    fun showAllUser(users: List<User>)
    fun showEmptyUser()
    fun showErrorMessage(errorMessage: String)

}
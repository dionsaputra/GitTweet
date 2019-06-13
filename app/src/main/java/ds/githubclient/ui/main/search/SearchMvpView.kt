package ds.githubclient.ui.main.search

import ds.githubclient.data.network.User

interface SearchMvpView {

    fun showAllUser(users: List<User>)
    fun showErrorMessage(errorMessage: String)
    fun showCompleteMessage(completeMessage: String)

}
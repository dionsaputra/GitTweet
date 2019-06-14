package ds.githubclient.ui.main.search.contract

import ds.githubclient.data.network.model.User

interface SearchMvpInteractor {

    fun getAllUsers(onComplete: (List<User>?, String?) -> Unit)

    fun filterUsers(searchQuery: String, onComplete: (List<User>?, String?) -> Unit)
}
package ds.githubclient.ui.main.search.interactor

import ds.githubclient.data.network.model.User

interface SearchMvpInteractor {

    fun getAllUsers(sinceId: Long, paginationSize: Int, onComplete: (List<User>?, String?) -> Unit)

    fun filterUsers(searchQuery: String, onComplete: (List<User>?, String?) -> Unit)
}
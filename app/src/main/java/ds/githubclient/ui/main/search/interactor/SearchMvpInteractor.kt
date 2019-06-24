package ds.githubclient.ui.main.search.interactor

import ds.githubclient.data.network.model.SearchResponse
import ds.githubclient.data.network.model.User

interface SearchMvpInteractor {

    fun listUsers(since: Long, perPage: Int, onComplete: (List<User>?, Throwable?) -> Unit)
    fun filterUsers(query: String, page: Int, perPage: Int, onComplete: (SearchResponse<List<User>?>?, Throwable?) -> Unit)

}
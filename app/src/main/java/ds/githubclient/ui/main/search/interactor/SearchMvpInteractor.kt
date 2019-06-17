package ds.githubclient.ui.main.search.interactor

import ds.githubclient.data.network.model.SearchResponse
import ds.githubclient.data.network.model.User

interface SearchMvpInteractor {

    fun getAllUser(since: Long, perPage: Int, onComplete: (List<User>?, Throwable?) -> Unit)
    fun searchUser(query: String, page: Int, perPage: Int, onComplete: (SearchResponse<List<User>?>?, Throwable?) -> Unit)

}
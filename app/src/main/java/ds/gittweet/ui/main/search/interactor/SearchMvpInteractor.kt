package ds.gittweet.ui.main.search.interactor

import ds.gittweet.data.remote.response.SearchResponse
import ds.gittweet.data.remote.response.UserResponse

interface SearchMvpInteractor {

    fun listUsers(since: Long, perPage: Int, onComplete: (List<UserResponse>?, Throwable?) -> Unit)
    fun filterUsers(query: String, page: Int, perPage: Int, onComplete: (SearchResponse<List<UserResponse>?>?, Throwable?) -> Unit)

}